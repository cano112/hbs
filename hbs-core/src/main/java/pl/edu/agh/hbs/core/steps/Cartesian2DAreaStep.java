package pl.edu.agh.hbs.core.steps;

import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.hbs.core.model.Area;
import pl.edu.agh.hbs.core.model.cartesian.client.*;
import pl.edu.agh.hbs.core.model.events.FramePreparedEvent;
import pl.edu.agh.hbs.core.model.events.StepCompletedEvent;
import pl.edu.agh.hbs.core.providers.SimulationStateProvider;
import pl.edu.agh.hbs.model.Agent;
import pl.edu.agh.hbs.model.skill.Message;
import pl.edu.agh.hbs.model.skill.Modifier;
import pl.edu.agh.hbs.model.skill.basic.modifier.ModPosition;
import scala.Function1;
import scala.collection.JavaConverters;
import scala.collection.Seq;
import scala.runtime.AbstractFunction1;

import java.util.*;

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
        log.info("Step: " + stateProvider.getStepsNumber(areaId));
        Area area = stateProvider.getAreaById(areaId);

        List<Message> messages = Collections.emptyList();
        Seq<Message> inMessages = JavaConverters.collectionAsScalaIterableConverter(messages).asScala().toSeq();
        List<Message> outMessages = new ArrayList<>();

        area.getAgents().forEach(a -> a.beforeStep(inMessages));
        area.getAgents().forEach(Agent::step);
        area.getAgents().forEach(a -> outMessages.addAll(JavaConverters.asJavaCollectionConverter(a.afterStep()).asJavaCollection()));

        stateProvider.setAreaById(areaId, area);
    }

    @Override
    public void afterStep(String areaId) {
        List<Agent> agents = stateProvider.getAreaById(areaId).getAgents();
        List<Body> bodies = new LinkedList<>();

        Function1<Modifier, Object> positionFilter = new AbstractFunction1<Modifier, Object>() {
            public Boolean apply(Modifier value) {
                return value instanceof ModPosition;
            }
        };

        agents.forEach(agent -> {
            ModPosition position = (ModPosition) agent.modifiers().toStream().find(positionFilter).get();
            bodies.add(new Body(
                    new Position(position.x(), position.y()),
                    Color.values()[0].getValue(),
                    AgentViewShape.values()[0].getName()));
        });

        eventBus.post(new FramePreparedEvent(new Frame(bodies), areaId));
        eventBus.post(new StepCompletedEvent(areaId));
    }
}
