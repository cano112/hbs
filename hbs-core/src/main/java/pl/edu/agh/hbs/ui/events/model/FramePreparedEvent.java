package pl.edu.agh.hbs.ui.events.model;

import pl.edu.agh.hbs.state.events.model.Event;
import pl.edu.agh.hbs.ui.dto.ViewFrame;

public final class FramePreparedEvent extends Event {
    private final ViewFrame viewFrame;

    public FramePreparedEvent(ViewFrame viewFrame) {
        this.viewFrame = viewFrame;
    }

    public ViewFrame getViewFrame() {
        return viewFrame;
    }
}
