package pl.edu.agh.hbs.api.ui.events.model;

import pl.edu.agh.hbs.api.events.Event;
import pl.edu.agh.hbs.api.ui.dto.ViewFrame;

public final class FramePreparedEvent extends Event {
    private final ViewFrame viewFrame;

    public FramePreparedEvent(ViewFrame viewFrame) {
        this.viewFrame = viewFrame;
    }

    public ViewFrame getViewFrame() {
        return viewFrame;
    }
}
