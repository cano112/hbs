package pl.edu.agh.hbs.core.model.domain.cartesian;

import pl.edu.agh.hbs.core.model.domain.Area;
import pl.edu.agh.hbs.core.model.domain.AreaBordersDefinition;
import pl.edu.agh.hbs.core.model.domain.step.Step;
import pl.edu.agh.hbs.model.Agent;

import java.util.Collection;

public class Cartesian2DArea extends Area {

    public Cartesian2DArea(final String areaId,
                           final Step step,
                           final AreaBordersDefinition areaBordersDefinition,
                           final Collection<Agent> agents) {
        super(areaId, step, areaBordersDefinition, agents);
    }
}
