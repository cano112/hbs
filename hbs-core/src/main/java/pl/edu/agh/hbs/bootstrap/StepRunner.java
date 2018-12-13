package pl.edu.agh.hbs.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.edu.agh.hbs.simulation.api.Step;
import pl.edu.agh.hbs.simulation.api.StopCondition;
import pl.edu.agh.hbs.state.AreaStepStage;
import pl.edu.agh.hbs.state.SimulationStateProvider;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Runner for a single area. Runs area steps - in sync with other areas.
 */
@Component
@Scope("prototype")
public class StepRunner implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(StepRunner.class);

    private final String areaId;
    private final Step step;
    private final StopCondition stopCondition;
    private final SimulationStateProvider simulationStateProvider;

    public StepRunner(final SimulationStateProvider simulationStateProvider,
                      final Step step,
                      final StopCondition stopCondition,
                      final String areaId) {
        this.simulationStateProvider = checkNotNull(simulationStateProvider);
        this.step = checkNotNull(step);
        this.stopCondition = checkNotNull(stopCondition);
        this.areaId = areaId;
    }

    @Override
    public void run() {
        while (true) {
            if (stopCondition.isReached(areaId)) {
                log.info("Stop condition reached for area: {}", areaId);
                break;
            }

            // Cannot make it reactive (eg. with the usage of EntryUpdatedEventListener) - we need synchronized access
            while (!simulationStateProvider.isTokenAvailable(AreaStepStage.STEP)) ;
            simulationStateProvider.getToken(AreaStepStage.STEP);
            step.step(areaId);


            while (!simulationStateProvider.isTokenAvailable(AreaStepStage.AFTER_STEP)) ;
            simulationStateProvider.getToken(AreaStepStage.AFTER_STEP);
            step.afterStep(areaId);
        }
    }

    public String getAreaId() {
        return areaId;
    }
}
