package pl.edu.agh.hbs.core.providers;

import pl.edu.agh.hbs.core.model.Area;

public interface SimulationStateProvider {
    int getStepsNumber(String areaId);

    void incrementStepsNumber(String areaId);

    Area getAreaById(String areaId);

    void setAreaById(String areaId, Area area);
}
