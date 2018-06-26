package pl.edu.agh.hbs.core.model.cartesian;

import pl.edu.agh.hbs.core.model.Area;
import pl.edu.agh.hbs.core.steps.Step;
import pl.edu.agh.hbs.model.Agent;

import java.util.List;

public class Cartesian2DArea extends Area {
    public Cartesian2DArea(final String areaId,
                           final Step step,
                           final List<Agent> agents) {
        super(areaId, step, agents);
    }
}
