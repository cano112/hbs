package pl.edu.agh.hbs.state.events.model;

public final class StepCompletedEvent extends Event {
    private final String areaId;

    public StepCompletedEvent(final String areaId) {
        this.areaId = areaId;
    }

    public String getAreaId() {
        return areaId;
    }
}
