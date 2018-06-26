package pl.edu.agh.hbs.core.stop_condition;


import pl.edu.agh.hbs.core.providers.impl.SimulationStateProviderImpl;


public class StepsNumberStopCondition implements StopCondition {

    private final int stepsLimit;

    private final SimulationStateProviderImpl stateProvider;

    public StepsNumberStopCondition(SimulationStateProviderImpl stateProvider, int stepsLimit) {
        this.stepsLimit = stepsLimit;
        this.stateProvider = stateProvider;
    }

    @Override
    public boolean isReached(String id) {
        return stateProvider.getStepsNumber(id) >= stepsLimit;
    }
}
