package pl.edu.agh.hbs.core.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.agh.hbs.core.model.cartesian.client.*;
import pl.edu.agh.hbs.core.providers.impl.SimulationStateProviderImpl;
import pl.edu.agh.hbs.core.websocket.SimulationWebSocketServer;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Cartesian2DAreaStep implements Step {

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
        List<Body> bodies = new LinkedList<>();
        Random random = new Random();
        for(int i = 0; i < 20; i++) {
            bodies.add(new Body(
                    new Position(random.nextInt(1300), random.nextInt(700)),
                    Color.values()[random.nextInt(Color.values().length)].getValue(),
                    AgentViewShape.values()[random.nextInt(AgentViewShape.values().length)].getName()));
        }
        Frame frame = new Frame(bodies);
        try {
            webSocketServer.broadcast(objectMapper.writeValueAsString(frame));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterStep(String areaId) {
        stateProvider.incrementStepsNumber(areaId);
    }
}
