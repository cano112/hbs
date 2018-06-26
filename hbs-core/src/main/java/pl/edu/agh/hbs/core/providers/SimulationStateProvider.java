package pl.edu.agh.hbs.core.providers;

public interface SimulationStateProvider {
    int getStepsNumber(String areaId);
    void incrementStepsNumber(String areaId);
}
