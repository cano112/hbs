package pl.edu.agh.hbs.state.events.model;

public final class StopConditionReachedEvent extends Event {
    private final String areaId;

    public StopConditionReachedEvent(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaId() {
        return areaId;
    }
}
