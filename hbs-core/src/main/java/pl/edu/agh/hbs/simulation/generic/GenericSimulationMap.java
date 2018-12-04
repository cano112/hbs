package pl.edu.agh.hbs.simulation.generic;

import pl.edu.agh.hbs.simulation.api.Area;
import pl.edu.agh.hbs.simulation.api.SimulationMap;

import java.util.List;

public class GenericSimulationMap extends SimulationMap {
    public GenericSimulationMap(List<? extends Area> areas) {
        super(areas);
    }

}
