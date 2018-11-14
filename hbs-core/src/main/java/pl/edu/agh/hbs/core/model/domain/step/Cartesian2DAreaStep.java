package pl.edu.agh.hbs.core.model.domain.step;

import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.hbs.core.event.domain.model.StepCompletedEvent;
import pl.edu.agh.hbs.core.model.domain.Area;
import pl.edu.agh.hbs.core.model.domain.AreaBordersDefinition;
import pl.edu.agh.hbs.core.service.AreaService;
import pl.edu.agh.hbs.core.state.SimulationStateProvider;
import pl.edu.agh.hbs.model.Agent;
import pl.edu.agh.hbs.model.StepOutput;
import pl.edu.agh.hbs.model.skill.Message;
import scala.collection.JavaConverters;
import scala.collection.Seq;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

@Component("cartesianStep")
public class Cartesian2DAreaStep implements Step {

    private static final Logger log = LoggerFactory.getLogger(Cartesian2DAreaStep.class);

    private final EventBus eventBus;
    private final SimulationStateProvider stateProvider;
    private final AreaService areaService;

    @Autowired
    public Cartesian2DAreaStep(final EventBus eventBus,
                               final SimulationStateProvider stateProvider,
                               final AreaService areaService) {
        this.eventBus = checkNotNull(eventBus);
        this.stateProvider = checkNotNull(stateProvider);
        this.areaService = checkNotNull(areaService);
    }

    @Override
    public void beforeStep(String areaId) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void step(String areaId) {
        // TODO: think about logging level - calling stateProvider is SLOW
        log.debug("Step: {}, area: {}", stateProvider.getStepsNumber(areaId), areaId);
        Area area = stateProvider.getAreaById(areaId);
        Seq<Message> inMessages = JavaConverters.collectionAsScalaIterableConverter(area.getMessages()).asScala().toSeq();
        List<Message> outMessages = new ArrayList<>();

        area.getAgents().forEach(a -> a.beforeStep(inMessages));
        area.getAgents().forEach(a -> {
            a.step();
            if (!area.isInside(a.position())) {
                area.removeAgent(a);
                putAgentToMatchingArea(a);
            }
        });
        area.getAgents().forEach(a -> {
            final StepOutput agentStepOutput = a.afterStep();
            outMessages.addAll(JavaConverters.asJavaCollection(agentStepOutput.messages()));
            JavaConverters.asJavaCollection(agentStepOutput.agents()).forEach(this::putAgentToMatchingArea);
        });
        area.clearMessages();
        area.addMessages(outMessages);
        addMessagesToNeighbourAreas(area, outMessages);
        stateProvider.setAreaById(areaId, area);
    }

    private void putAgentToMatchingArea(Agent agent) {
        final Map<String, AreaBordersDefinition> borderDefinitions = stateProvider.getAreaBorderDefinitions();
        areaService.getAreaIdByPosition(borderDefinitions, agent.position()).ifPresent(areaId -> {
            Area area = stateProvider.getAreaById(areaId);
            area.addAgent(agent);
            stateProvider.setAreaById(areaId, area);
        });
    }

    private void addMessagesToNeighbourAreas(Area area, List<Message> messages) {
        area.getNeighourAreas().forEach(areaId -> {
            final Area neighbourArea = stateProvider.getAreaById(areaId);
            neighbourArea.addMessages(messages);
            stateProvider.setAreaById(areaId, neighbourArea);
        });
    }

    @Override
    public void afterStep(String areaId) {
        eventBus.post(new StepCompletedEvent(areaId));
    }
}
