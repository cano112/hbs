package pl.edu.agh.hbs.core.event.state.listeners;

import com.google.common.eventbus.EventBus;
import com.hazelcast.core.EntryEvent;
import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.map.listener.EntryUpdatedListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.hbs.core.event.domain.model.AreaStepsSynchronizedEvent;
import pl.edu.agh.hbs.core.model.state.StateFlag;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class AreasSynchronizedUpdateEventListener implements EntryUpdatedListener<StateFlag, Boolean>,
        EntryAddedListener<StateFlag, Boolean> {

    private static final Logger log = LoggerFactory.getLogger(AreasSynchronizedUpdateEventListener.class);

    private final EventBus eventBus;

    @Autowired
    public AreasSynchronizedUpdateEventListener(final EventBus eventBus) {
        this.eventBus = checkNotNull(eventBus);
    }

    @Override
    public void entryUpdated(EntryEvent<StateFlag, Boolean> event) {
        log.debug("{} updated from {} to {}", event.getKey(), event.getOldValue(), event.getValue());
        if (event.getValue()) {
            eventBus.post(new AreaStepsSynchronizedEvent());
        }
    }

    @Override
    public void entryAdded(EntryEvent<StateFlag, Boolean> event) {
        log.debug("{} initialized with value:", event.getValue());
        if (event.getValue()) {
            eventBus.post(new AreaStepsSynchronizedEvent());
        }
    }
}
