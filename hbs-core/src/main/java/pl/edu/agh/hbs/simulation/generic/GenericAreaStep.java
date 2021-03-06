package pl.edu.agh.hbs.simulation.generic;

import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.edu.agh.hbs.exceptions.SimulationExecutionException;
import pl.edu.agh.hbs.model.Agent;
import pl.edu.agh.hbs.model.StepOutput;
import pl.edu.agh.hbs.model.skill.Message;
import pl.edu.agh.hbs.simulation.api.Area;
import pl.edu.agh.hbs.simulation.api.AreaBordersDefinition;
import pl.edu.agh.hbs.simulation.api.MessageDistributionStrategy;
import pl.edu.agh.hbs.simulation.api.Step;
import pl.edu.agh.hbs.state.AreaStateService;
import pl.edu.agh.hbs.state.SimulationStateProvider;
import pl.edu.agh.hbs.state.events.model.StepCompletedEvent;
import pl.edu.agh.hbs.utils.PropertiesUtils;
import pl.edu.agh.hbs.utils.ScalaInterop;
import scala.collection.Seq;

import java.util.*;
import java.util.function.Consumer;

import static com.google.common.base.Preconditions.checkNotNull;

@Component("genericStep")
public class GenericAreaStep implements Step {

    private static final Logger log = LoggerFactory.getLogger(GenericAreaStep.class);

    private static final String DELAY_ENABLED_PROPERTIES_KEY = "hbs.simulation.step.delay.enabled";
    private static final String DELAY_DURATION_PROPERTIES_KEY = "hbs.simulation.step.delay.duration";
    private final EventBus eventBus;
    private final SimulationStateProvider stateProvider;
    private final AreaStateService areaStateService;
    private final MessageDistributionStrategy messageDistributionStrategy;
    private final Properties simulationProperties;

    @Autowired
    public GenericAreaStep(final EventBus eventBus,
                           final SimulationStateProvider stateProvider,
                           final AreaStateService areaStateService,
                           final MessageDistributionStrategy messageDistributionStrategy,
                           @Qualifier("simulationProperties") final Properties simulationProperties) {
        this.eventBus = checkNotNull(eventBus);
        this.stateProvider = checkNotNull(stateProvider);
        this.areaStateService = checkNotNull(areaStateService);
        this.messageDistributionStrategy = checkNotNull(messageDistributionStrategy);
        this.simulationProperties = simulationProperties;
    }

    private void delayStep() {
        final boolean delayEnabled = PropertiesUtils
                .getBooleanValue(simulationProperties, DELAY_ENABLED_PROPERTIES_KEY);
        if (delayEnabled) {
            final int delayDuration = PropertiesUtils
                    .getIntegerValue(simulationProperties, DELAY_DURATION_PROPERTIES_KEY);
            try {
                Thread.sleep(delayDuration);
            } catch (InterruptedException e) {
                throw new SimulationExecutionException("Step delay sleep has been interrupted", e);
            }
        }
    }

    @Override
    public void step(String areaId) {
        delayStep();
        log.debug("Step: {}, area: {}", stateProvider.getStepsNumber(areaId), areaId);
        try {
            stateProvider.lockArea(areaId);
            final Area area = stateProvider.getAreaById(areaId);
            final Collection<Message> inMessages = area.getMessages();
            area.getAgents().forEach(a -> {
                Seq<Message> filteredMessages = messageDistributionStrategy.filterForAgent(a, inMessages);
                a.beforeStep(filteredMessages);
            });

            area.getAgents().forEach(Agent::step);
            stateProvider.setAreaById(areaId, area);
        } finally {
            stateProvider.unlockArea(areaId);
        }
    }

    @Override
    public void afterStep(final String areaId) {
        log.debug("After step: {}, area: {}", stateProvider.getStepsNumber(areaId), areaId);
        final Collection<Message> outMessages = new LinkedList<>();
        final Map<String, Collection<Agent>> agentsToAdd = new HashMap<>();
        final Map<String, Collection<Agent>> agentsToRemove = new HashMap<>();

        stateProvider.lockArea(areaId);
        final Area area = stateProvider.getAreaById(areaId);
        area.getAgents().forEach(agent -> {
            final StepOutput agentStepOutput = agent.afterStep();
            if (agentStepOutput.isDead()) {
                putToMap(agentsToRemove, areaId, agent);
            } else {
                moveAgentToMatchingArea(area, agent, agentsToAdd, agentsToRemove);
            }

            putChildrenOnMap(agentStepOutput.agents(), agentsToAdd);

            final Collection<Message> agentOutMessages = ScalaInterop.toCollection(agentStepOutput.messages());
            outMessages.addAll(agentOutMessages);
        });

        final Map<String, Collection<Message>> messagesToAdd =
                messageDistributionStrategy.distributeToAreas(area, outMessages);

        area.clearMessages();
        area.addMessages(messagesToAdd.getOrDefault(areaId, Collections.emptySet()));
        area.addAgents(agentsToAdd.getOrDefault(areaId, Collections.emptySet()));
        area.removeAgents(agentsToRemove.getOrDefault(areaId, Collections.emptySet()));

        // We have to remove these objects from maps, otherwise they would be overwritten.
        messagesToAdd.remove(areaId);
        agentsToAdd.remove(areaId);
        messagesToAdd.remove(areaId);

        stateProvider.setAreaById(areaId, area);
        stateProvider.unlockArea(areaId);

        agentsToAdd.forEach((id, ag) -> updateArea(id, ar -> ar.addAgents(ag)));
        agentsToRemove.forEach((id, ag) -> updateArea(id, ar -> ar.removeAgents(ag)));
        messagesToAdd.forEach((id, mes) -> updateArea(id, ar -> ar.addMessages(mes)));

        eventBus.post(new StepCompletedEvent(areaId));
    }

    private void moveAgentToMatchingArea(final Area area, final Agent agent,
                                         final Map<String, Collection<Agent>> agentsToAdd,
                                         final Map<String, Collection<Agent>> agentsToRemove) {
        if (!area.isInside(agent.position())) {
            putToMap(agentsToRemove, area.getAreaId(), agent);
            putAgentToMatchingArea(agent, agentsToAdd);
        }
    }

    private void putChildrenOnMap(final Seq<Agent> children, final Map<String, Collection<Agent>> agentsToAdd) {
        ScalaInterop.toCollection(children).forEach(child -> putAgentToMatchingArea(child, agentsToAdd));
    }

    private void putAgentToMatchingArea(final Agent agent, final Map<String, Collection<Agent>> agentsToAdd) {
        final Map<String, AreaBordersDefinition> borderDefinitions = stateProvider.getAreaBorderDefinitions();
        areaStateService.getAreaIdByPosition(borderDefinitions, agent.position())
                .ifPresent(areaId -> putToMap(agentsToAdd, areaId, agent));
    }

    private <T> void putToMap(final Map<String, Collection<T>> map, String areaId, T object) {
        if (map != null) {
            if (!map.containsKey(areaId)) {
                final Set<T> set = new HashSet<>();
                set.add(object);
                map.put(areaId, set);
            } else {
                Collection<T> values = map.get(areaId);
                values.add(object);
            }
        }
    }

    private void updateArea(final String areaId, Consumer<Area> consumer) {
        stateProvider.lockArea(areaId);
        final Area area = stateProvider.getAreaById(areaId);
        consumer.accept(area);
        stateProvider.setAreaById(areaId, area);
        stateProvider.unlockArea(areaId);
    }
}
