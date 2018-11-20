package pl.edu.agh.hbs.api;

import java.util.List;

public abstract class SimulationMap {
    private final List<? extends Area> areas;

    public SimulationMap(List<? extends Area> areas) {
        this.areas = areas;
    }

    public List<? extends Area> getAreas() {
        return this.areas;
    }


}
