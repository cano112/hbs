package pl.edu.agh.hbs.simulation.generic.stop;

import com.google.common.eventbus.EventBus;
import pl.edu.agh.hbs.simulation.api.StopCondition;
import pl.edu.agh.hbs.state.SimulationStateProvider;
import pl.edu.agh.hbs.state.SimulationStateProviderImpl;
import pl.edu.agh.hbs.state.events.model.StopConditionReachedEvent;

import static com.google.common.base.Preconditions.checkNotNull;

public class StepsNumberStopCondition implements StopCondition {

    private final int stepsLimit;
    private final SimulationStateProvider stateProvider;
    private final EventBus eventBus;

    public StepsNumberStopCondition(final SimulationStateProviderImpl stateProvider,
                                    final EventBus eventBus,
                                    final int stepsLimit) {
        this.stepsLimit = stepsLimit;
        this.stateProvider = checkNotNull(stateProvider);
        this.eventBus = checkNotNull(eventBus);
    }

    @Override
    public boolean isReached(String id) {
        if (stateProvider.getStepsNumber(id) >= stepsLimit) {
            eventBus.post(new StopConditionReachedEvent(id));
            return true;
        }
        return false;
    }
}
