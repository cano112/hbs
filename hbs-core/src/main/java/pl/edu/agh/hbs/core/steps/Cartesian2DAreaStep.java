package pl.edu.agh.hbs.core.steps;

import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.hbs.core.model.Area;
import pl.edu.agh.hbs.core.model.cartesian.client.Body;
import pl.edu.agh.hbs.core.model.cartesian.client.Color;
import pl.edu.agh.hbs.core.model.cartesian.client.ViewFrame;
import pl.edu.agh.hbs.core.model.cartesian.client.ViewPosition;
import pl.edu.agh.hbs.core.model.events.FramePreparedEvent;
import pl.edu.agh.hbs.core.model.events.StepCompletedEvent;
import pl.edu.agh.hbs.core.providers.Representation;
import pl.edu.agh.hbs.core.providers.SimulationStateProvider;
import pl.edu.agh.hbs.model.Agent;
import pl.edu.agh.hbs.model.skill.Message;
import scala.collection.JavaConverters;
import scala.collection.Seq;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Component("cartesianStep")
public class Cartesian2DAreaStep implements Step {

    private static final Logger log = LoggerFactory.getLogger(Cartesian2DAreaStep.class);

    private final EventBus eventBus;
    private final SimulationStateProvider stateProvider;

    @Autowired
    public Cartesian2DAreaStep(final EventBus eventBus,
                               final SimulationStateProvider stateProvider) {
        this.eventBus = checkNotNull(eventBus);
        this.stateProvider = checkNotNull(stateProvider);
    }

    @Override
    public void beforeStep(String areaId) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void step(String areaId) {
        log.info("Step: {}", stateProvider.getStepsNumber(areaId));
        Area area = stateProvider.getAreaById(areaId);
        Seq<Message> inMessages = JavaConverters.collectionAsScalaIterableConverter(area.getMessages()).asScala().toSeq();
        List<Message> outMessages = new ArrayList<>();

        area.getAgents().forEach(a -> a.beforeStep(inMessages));
        area.getAgents().forEach(Agent::step);
        area.getAgents().forEach(a ->
                outMessages.addAll(JavaConverters.asJavaCollectionConverter(a.afterStep()).asJavaCollection()));
        area.clearMessages();
        area.addMessages(outMessages);
        stateProvider.setAreaById(areaId, area);
    }

    @Override
    public void afterStep(String areaId) {
        List<Agent> agents = stateProvider.getAreaById(areaId).getAgents();
        List<Body> bodies = new LinkedList<>();
        agents.forEach(agent -> {
            pl.edu.agh.hbs.model.Position position = agent.position();
            Representation representation = agent.representation();
            ViewPosition viewPosition = new ViewPosition(position.get(0), position.get(1));
            bodies.add(new Body(
                    viewPosition,
                    Color.values()[0].getValue(),
                    representation.getIdentity()));
        });

        eventBus.post(new FramePreparedEvent(new ViewFrame(bodies), areaId));
        eventBus.post(new StepCompletedEvent(areaId));
    }
}
