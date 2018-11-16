package pl.edu.agh.hbs.core.simulation.generic;

import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.hbs.core.api.*;
import pl.edu.agh.hbs.core.simulation.state.AreaStateService;
import pl.edu.agh.hbs.core.simulation.state.events.model.StepCompletedEvent;
import pl.edu.agh.hbs.core.utils.ScalaInterop;
import pl.edu.agh.hbs.model.Agent;
import pl.edu.agh.hbs.model.StepOutput;
import pl.edu.agh.hbs.model.skill.Message;
import scala.collection.Seq;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

@Component("genericStep")
public class GenericAreaStep implements Step {

    private static final Logger log = LoggerFactory.getLogger(GenericAreaStep.class);

    private final EventBus eventBus;
    private final SimulationStateProvider stateProvider;
    private final AreaStateService areaStateService;
    private final MessageDistributionStrategy messageDistributionStrategy;

    @Autowired
    public GenericAreaStep(final EventBus eventBus,
                           final SimulationStateProvider stateProvider,
                           final AreaStateService areaStateService,
                           final MessageDistributionStrategy messageDistributionStrategy) {
        this.eventBus = checkNotNull(eventBus);
        this.stateProvider = checkNotNull(stateProvider);
        this.areaStateService = checkNotNull(areaStateService);
        this.messageDistributionStrategy = checkNotNull(messageDistributionStrategy);
    }

    @Override
    public void beforeStep(String areaId) {
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void step(String areaId) {
        log.debug("Step: {}, area: {}", stateProvider.getStepsNumber(areaId), areaId);
        final Area area = stateProvider.getAreaById(areaId);
        final Collection<Message> inMessages = area.getMessages();
        final Collection<Message> outMessages = new LinkedList<>();

        area.getAgents().forEach(a -> {
            Seq<Message> filteredMessages = messageDistributionStrategy.filterForAgent(a, inMessages);
            a.beforeStep(filteredMessages);
        });
        area.getAgents().forEach(Agent::step);
        area.getAgents().forEach(agent -> {
            final StepOutput agentStepOutput = agent.afterStep();
            if (agentStepOutput.isDead()) {
                area.removeAgent(agent);
            } else {
                moveAgentToMatchingArea(area, agent);
            }
            putChildrenOnMap(agentStepOutput.agents());

            final Collection<Message> agentOutMessages = ScalaInterop.toCollection(agentStepOutput.messages());
            outMessages.addAll(agentOutMessages);
        });

        area.clearMessages();
        stateProvider.setAreaById(areaId, area);
        distributeMessagesToAreas(area, outMessages);
    }

    private void moveAgentToMatchingArea(final Area area, final Agent agent) {
        if (!area.isInside(agent.position())) {
            area.removeAgent(agent);
            putAgentToMatchingArea(agent);
        }
    }

    private void putChildrenOnMap(final Seq<Agent> children) {
        ScalaInterop.toCollection(children).forEach(this::putAgentToMatchingArea);
    }

    private void putAgentToMatchingArea(final Agent agent) {
        final Map<String, AreaBordersDefinition> borderDefinitions = stateProvider.getAreaBorderDefinitions();
        areaStateService.getAreaIdByPosition(borderDefinitions, agent.position()).ifPresent(areaId -> {
            Area area = stateProvider.getAreaById(areaId);
            area.addAgent(agent);
            stateProvider.setAreaById(areaId, area);
        });
    }

    private void distributeMessagesToAreas(Area area, Collection<Message> messages) {
        Map<String, Collection<Message>> distribution = messageDistributionStrategy.distributeToAreas(area, messages);
        distribution.forEach((targetAreaId, areaMessages) -> {
            final Area targetArea = stateProvider.getAreaById(targetAreaId);
            targetArea.addMessages(areaMessages);
            stateProvider.setAreaById(targetAreaId, targetArea);
        });
    }

    @Override
    public void afterStep(String areaId) {
        eventBus.post(new StepCompletedEvent(areaId));
    }
}
