package pl.edu.agh.hbs.core.model.domain.cartesian;

import pl.edu.agh.hbs.core.model.domain.Area;
import pl.edu.agh.hbs.core.model.domain.SimulationMap;

import java.util.List;

public class Cartesian2DSimulationMap extends SimulationMap {
    public Cartesian2DSimulationMap(List<? extends Area> areas) {
        super(areas);
    }

}
