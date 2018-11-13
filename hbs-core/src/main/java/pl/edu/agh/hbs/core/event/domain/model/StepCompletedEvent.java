package pl.edu.agh.hbs.core.event.domain.model;

import pl.edu.agh.hbs.core.event.Event;

public final class StepCompletedEvent extends Event {
    private final String areaId;

    public StepCompletedEvent(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaId() {
        return areaId;
    }
}
