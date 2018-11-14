package pl.edu.agh.hbs.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.hbs.core.model.Representation;
import pl.edu.agh.hbs.core.model.ui.Color;
import pl.edu.agh.hbs.core.model.ui.cartesian.Body;
import pl.edu.agh.hbs.core.model.ui.cartesian.ViewFrame;
import pl.edu.agh.hbs.core.model.ui.cartesian.ViewPosition;
import pl.edu.agh.hbs.core.state.SimulationStateProvider;
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
            Vector position = agent.position();
            Representation representation = agent.representation();
            ViewPosition viewPosition = new ViewPosition((int) position.get(0), (int) position.get(1));
            bodies.add(new Body(
                    viewPosition,
                    Color.values()[0].getValue(),
                    representation.getIdentity(),
                    (int) agent.rotation()));
        });

        return new ViewFrame(bodies);
    }
}
