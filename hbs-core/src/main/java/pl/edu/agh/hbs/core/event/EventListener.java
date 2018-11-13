package pl.edu.agh.hbs.core.event;

import com.google.common.eventbus.EventBus;

import javax.annotation.PostConstruct;

public abstract class EventListener {

    protected final EventBus eventBus;

    public EventListener(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @PostConstruct
    public void init() {
        eventBus.register(this);
    }
}
