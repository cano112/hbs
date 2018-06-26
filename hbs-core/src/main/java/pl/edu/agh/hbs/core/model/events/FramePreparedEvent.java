package pl.edu.agh.hbs.core.model.events;

import pl.edu.agh.hbs.core.model.cartesian.client.Frame;

public final class FramePreparedEvent extends Event {
    private final Frame frame;
    private final String areaId;

    public FramePreparedEvent(Frame frame, String areaId) {
        this.frame = frame;
        this.areaId = areaId;
    }

    public Frame getFrame() {
        return frame;
    }

    public String getAreaId() {
        return areaId;
    }
}
