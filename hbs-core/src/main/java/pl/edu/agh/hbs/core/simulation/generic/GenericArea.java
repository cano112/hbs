package pl.edu.agh.hbs.core.simulation.generic;

import pl.edu.agh.hbs.core.api.Area;
import pl.edu.agh.hbs.core.api.AreaBordersDefinition;
import pl.edu.agh.hbs.core.api.Step;
import pl.edu.agh.hbs.model.Agent;

import java.util.Collection;

public class GenericArea extends Area {

    public GenericArea(final String areaId,
                       final Step step,
                       final AreaBordersDefinition areaBordersDefinition,
                       final Collection<Agent> agents) {
        super(areaId, step, areaBordersDefinition, agents);
    }
}
