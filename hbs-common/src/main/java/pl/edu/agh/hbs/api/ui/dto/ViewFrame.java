package pl.edu.agh.hbs.api.ui.dto;

import java.util.List;

public class ViewFrame {
    private List<Body> bodies;

    public ViewFrame() {
    }

    public ViewFrame(List<Body> bodies) {
        this.bodies = bodies;
    }

    public List<Body> getBodies() {
        return bodies;
    }

    public void setBodies(List<Body> bodies) {
        this.bodies = bodies;
    }
}
