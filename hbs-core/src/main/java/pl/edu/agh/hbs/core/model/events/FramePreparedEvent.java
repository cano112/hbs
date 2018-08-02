package pl.edu.agh.hbs.core.model.events;

import pl.edu.agh.hbs.core.model.cartesian.client.ViewFrame;

public final class FramePreparedEvent extends Event {
    private final ViewFrame viewFrame;
    private final String areaId;

    public FramePreparedEvent(ViewFrame viewFrame, String areaId) {
        this.viewFrame = viewFrame;
        this.areaId = areaId;
    }

    public ViewFrame getViewFrame() {
        return viewFrame;
    }

    public String getAreaId() {
        return areaId;
    }
}
