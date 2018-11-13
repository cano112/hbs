package pl.edu.agh.hbs.core.event.domain.model;

import pl.edu.agh.hbs.core.event.Event;
import pl.edu.agh.hbs.core.model.ui.cartesian.ViewFrame;

public final class FramePreparedEvent extends Event {
    private final ViewFrame viewFrame;

    public FramePreparedEvent(ViewFrame viewFrame) {
        this.viewFrame = viewFrame;
    }

    public ViewFrame getViewFrame() {
        return viewFrame;
    }
}
