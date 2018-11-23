package pl.edu.agh.hbs.simulation.state.events.listeners;

import com.google.common.eventbus.EventBus;
import com.hazelcast.core.EntryEvent;
import com.hazelcast.map.listener.EntryUpdatedListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pl.edu.agh.hbs.api.SimulationStateProvider;
import pl.edu.agh.hbs.api.ui.events.model.FramePreparedEvent;
import pl.edu.agh.hbs.api.ui.service.ViewService;
import pl.edu.agh.hbs.simulation.state.AreaStepStage;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class AreaStepBucketEventListener implements EntryUpdatedListener<AreaStepStage, Integer> {

    private final EventBus eventBus;
    private final SimulationStateProvider simulationStateProvider;
    private final ViewService viewService;


    @Autowired
    @Lazy
    public AreaStepBucketEventListener(final EventBus eventBus,
                                       final SimulationStateProvider simulationStateProvider,
                                       final ViewService viewService) {
        this.eventBus = checkNotNull(eventBus);
        this.simulationStateProvider = checkNotNull(simulationStateProvider);
        this.viewService = checkNotNull(viewService);
    }

    @Override
    public void entryUpdated(EntryEvent<AreaStepStage, Integer> event) {
        if (event.getValue() == 0) {
            switch (event.getKey()) {
                case BEFORE_STEP:
                    simulationStateProvider.setStepLatchCount(AreaStepStage.STEP);
                    break;
                case STEP:
                    simulationStateProvider.setStepLatchCount(AreaStepStage.AFTER_STEP);
                    break;
                case AFTER_STEP:
                    eventBus.post(new FramePreparedEvent(viewService.prepareViewFrame()));
                    simulationStateProvider.setStepLatchCount(AreaStepStage.BEFORE_STEP);
            }
        }
    }
}
