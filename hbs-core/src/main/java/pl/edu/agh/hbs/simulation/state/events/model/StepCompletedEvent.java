package pl.edu.agh.hbs.simulation.state.events.model;

import pl.edu.agh.hbs.api.events.Event;

public final class StepCompletedEvent extends Event {
    private final String areaId;

    public StepCompletedEvent(final String areaId) {
        this.areaId = areaId;
    }

    public String getAreaId() {
        return areaId;
    }
}
