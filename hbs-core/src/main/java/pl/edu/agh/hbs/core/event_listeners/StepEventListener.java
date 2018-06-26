package pl.edu.agh.hbs.core.event_listeners;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.hbs.core.model.events.StepCompletedEvent;
import pl.edu.agh.hbs.core.providers.SimulationStateProvider;

@Component
public class StepEventListener extends EventListener {

    private final SimulationStateProvider stateProvider;


    @Autowired
    public StepEventListener(final EventBus eventBus,
                             final SimulationStateProvider stateProvider) {
        super(eventBus);
        this.stateProvider = stateProvider;
    }

    @Subscribe
    public void onStepCompleted(StepCompletedEvent event) {
        stateProvider.incrementStepsNumber(event.getAreaId());
    }


}
