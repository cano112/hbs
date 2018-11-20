package pl.edu.agh.hbs.api.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.hbs.api.SimulationStateProvider;
import pl.edu.agh.hbs.api.ui.Representation;
import pl.edu.agh.hbs.api.ui.dto.Body;
import pl.edu.agh.hbs.api.ui.dto.Color;
import pl.edu.agh.hbs.api.ui.dto.ViewFrame;
import pl.edu.agh.hbs.api.ui.dto.ViewPosition;
import pl.edu.agh.hbs.model.Vector;

import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class ViewService {

    private final SimulationStateProvider stateProvider;

    @Autowired
    public ViewService(final SimulationStateProvider stateProvider) {
        this.stateProvider = checkNotNull(stateProvider);
    }

    /**
     * Prepares {@link ViewFrame} for current simulation state
     *
     * @return frame with all agents state
     */
    public ViewFrame prepareViewFrame() {
        List<Body> bodies = new LinkedList<>();
        stateProvider.getAllAgents().forEach(agent -> {
            final Vector position = agent.position();
            final Representation representation = agent.representation();
            final Color color = agent.color();
            final ViewPosition viewPosition = new ViewPosition((int) position.get(0), (int) position.get(1));
            bodies.add(new Body(
                    viewPosition,
                    color.getValue(),
                    representation.getIdentity(),
                    (int) agent.rotation()));
        });

        return new ViewFrame(bodies);
    }
}
