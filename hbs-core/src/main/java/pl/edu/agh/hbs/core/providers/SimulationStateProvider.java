package pl.edu.agh.hbs.core.providers;

import pl.edu.agh.age.compute.api.WorkerAddress;
import pl.edu.agh.hbs.core.model.Area;

public interface SimulationStateProvider {
    int getStepsNumber(String areaId);

    void incrementStepsNumber(String areaId);

    void initializeStepsNumber(String areaId);

    Area getAreaById(String areaId);

    void setAreaById(String areaId, Area area);

    WorkerAddress getAreaLocationByAreaId(String areaId);

    void setAreaLocationByAreaId(String areaId, WorkerAddress address);
}
