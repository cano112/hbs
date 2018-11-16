package pl.edu.agh.hbs.core.ui.events.model;

import pl.edu.agh.hbs.core.api.events.Event;
import pl.edu.agh.hbs.core.ui.dto.ViewFrame;

public final class FramePreparedEvent extends Event {
    private final ViewFrame viewFrame;

    public FramePreparedEvent(ViewFrame viewFrame) {
        this.viewFrame = viewFrame;
    }

    public ViewFrame getViewFrame() {
        return viewFrame;
    }
}
