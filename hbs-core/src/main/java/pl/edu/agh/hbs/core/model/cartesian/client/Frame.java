package pl.edu.agh.hbs.core.model.cartesian.client;

import java.util.List;

public class Frame {
    private List<Body> bodies;

    public Frame() {
    }

    public Frame(List<Body> bodies) {
        this.bodies = bodies;
    }

    public List<Body> getBodies() {
        return bodies;
    }

    public void setBodies(List<Body> bodies) {
        this.bodies = bodies;
    }
}
