package pl.edu.agh.hbs.state.events.listeners;

import com.google.common.eventbus.EventBus;
import com.hazelcast.core.EntryEvent;
import com.hazelcast.map.listener.EntryUpdatedListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pl.edu.agh.hbs.state.AreaStepStage;
import pl.edu.agh.hbs.state.SimulationStateProvider;
import pl.edu.agh.hbs.ui.events.model.FramePreparedEvent;
import pl.edu.agh.hbs.ui.service.ViewService;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class AreaStepLatchEventListener implements EntryUpdatedListener<AreaStepStage, Integer> {

    private final EventBus eventBus;
    private final SimulationStateProvider simulationStateProvider;
    private final ViewService viewService;

    @Autowired
    @Lazy
    public AreaStepLatchEventListener(final EventBus eventBus,
                                      final SimulationStateProvider simulationStateProvider,
                                      final ViewService viewService) {
        this.eventBus = checkNotNull(eventBus);
        this.simulationStateProvider = checkNotNull(simulationStateProvider);
        this.viewService = checkNotNull(viewService);
    }

    @Override
    public void entryUpdated(EntryEvent<AreaStepStage, Integer> event) {
        if (event.getValue() == 0 && simulationStateProvider.isMasterNode()) {
            switch (event.getKey()) {
                case STEP:
                    simulationStateProvider.setStepLatchCount(AreaStepStage.AFTER_STEP);
                    break;
                case AFTER_STEP:
                    eventBus.post(new FramePreparedEvent(viewService.prepareViewFrame()));
                    simulationStateProvider.setStepLatchCount(AreaStepStage.STEP);
            }
        }
    }
}
