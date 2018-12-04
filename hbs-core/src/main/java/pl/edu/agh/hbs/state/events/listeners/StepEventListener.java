package pl.edu.agh.hbs.state.events.listeners;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.hbs.state.SimulationStateProvider;
import pl.edu.agh.hbs.state.events.model.StepCompletedEvent;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
@SuppressWarnings("unused")
public class StepEventListener extends EventListener {

    private static final Logger log = LoggerFactory.getLogger(StepEventListener.class);

    private final SimulationStateProvider stateProvider;

    @Autowired
    public StepEventListener(final EventBus eventBus,
                             final SimulationStateProvider stateProvider) {
        super(eventBus);
        this.stateProvider = checkNotNull(stateProvider);
    }

    @Subscribe
    public void onStepCompleted(StepCompletedEvent event) {
        stateProvider.incrementStepsNumber(event.getAreaId());
    }
}
