package pl.edu.agh.hbs.simulation.state.events.model;

import pl.edu.agh.hbs.api.events.Event;

public final class StopConditionReachedEvent extends Event {
    private final String areaId;

    public StopConditionReachedEvent(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaId() {
        return areaId;
    }
}
