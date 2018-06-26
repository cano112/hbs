package pl.edu.agh.hbs.core.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.hbs.core.model.Area;
import pl.edu.agh.hbs.core.model.cartesian.client.*;
import pl.edu.agh.hbs.core.providers.impl.SimulationStateProviderImpl;
import pl.edu.agh.hbs.core.websocket.SimulationWebSocketServer;
import pl.edu.agh.hbs.model.Agent;
import pl.edu.agh.hbs.model.skill.Modifier;
import pl.edu.agh.hbs.model.skill.move.modifier.ModPosition;
import scala.Function1;
import scala.runtime.AbstractFunction1;

import java.util.LinkedList;
import java.util.List;

public class Cartesian2DAreaStep implements Step {

    private static final Logger log = LoggerFactory.getLogger(Step.class);

    private final SimulationStateProviderImpl stateProvider;
    private final SimulationWebSocketServer webSocketServer;
    private final ObjectMapper objectMapper;

    public Cartesian2DAreaStep(final SimulationStateProviderImpl stateProvider,
                               final SimulationWebSocketServer webSocketServer,
                               final ObjectMapper objectMapper) {
        this.stateProvider = stateProvider;
        this.webSocketServer = webSocketServer;

        this.objectMapper = objectMapper;
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
        List<Agent> agents = area.getAgents();

        agents.forEach(agent -> {
            agent.takeAction(agent.decide());
        });

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

        Frame frame = new Frame(bodies);
        try {
            log.info("Frame: " + objectMapper.writeValueAsString(frame));
            webSocketServer.broadcast(objectMapper.writeValueAsString(frame));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        stateProvider.incrementStepsNumber(areaId);
    }
}
