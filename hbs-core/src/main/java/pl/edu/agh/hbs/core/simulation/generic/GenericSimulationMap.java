package pl.edu.agh.hbs.core.simulation.generic;

import pl.edu.agh.hbs.core.api.Area;
import pl.edu.agh.hbs.core.api.SimulationMap;

import java.util.List;

public class GenericSimulationMap extends SimulationMap {
    public GenericSimulationMap(List<? extends Area> areas) {
        super(areas);
    }

}
