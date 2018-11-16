package pl.edu.agh.hbs.core.simulation.state.events.model;

import pl.edu.agh.hbs.core.api.events.Event;

public final class StepCompletedEvent extends Event {
    private final String areaId;

    public StepCompletedEvent(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaId() {
        return areaId;
    }
}
