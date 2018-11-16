package pl.edu.agh.hbs.core.simulation.state.events.listeners;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.hbs.core.api.SimulationStateProvider;
import pl.edu.agh.hbs.core.api.events.EventListener;
import pl.edu.agh.hbs.core.simulation.state.events.model.AreaStepsSynchronizedEvent;
import pl.edu.agh.hbs.core.simulation.state.events.model.StepCompletedEvent;
import pl.edu.agh.hbs.core.ui.dto.ViewFrame;
import pl.edu.agh.hbs.core.ui.events.model.FramePreparedEvent;
import pl.edu.agh.hbs.core.ui.service.ViewService;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
@SuppressWarnings("unused")
public class StepEventListener extends EventListener {

    private static final Logger log = LoggerFactory.getLogger(StepEventListener.class);

    private final SimulationStateProvider stateProvider;
    private final ViewService viewService;

    @Autowired
    public StepEventListener(final EventBus eventBus,
                             final SimulationStateProvider stateProvider,
                             final ViewService viewService) {
        super(eventBus);
        this.stateProvider = checkNotNull(stateProvider);
        this.viewService = checkNotNull(viewService);
    }

    @Subscribe
    public void onStepCompleted(StepCompletedEvent event) {
        stateProvider.incrementStepsNumber(event.getAreaId());
    }

    @Subscribe
    public void onStepsSynchronized(AreaStepsSynchronizedEvent event) {
        final ViewFrame frame = viewService.prepareViewFrame();
        eventBus.post(new FramePreparedEvent(frame));
    }
}
