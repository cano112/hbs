package pl.edu.agh.hbs.core.runners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.hbs.core.steps.Step;
import pl.edu.agh.hbs.core.stop_condition.StopCondition;

import static com.google.common.base.Preconditions.checkNotNull;

public class StepRunner implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(StepRunner.class);

    private final Step step;
    private final StopCondition stopCondition;
    private final long runnerId;
    private final String areaId;

    public StepRunner(final Step step,
                      final StopCondition stopCondition,
                      final long runnerId,
                      final String areaId) {
        this.step = checkNotNull(step);
        this.stopCondition = checkNotNull(stopCondition);
        this.runnerId = runnerId;
        this.areaId = areaId;
    }

    @Override
    public void run() {
        while(!stopCondition.isReached(areaId)) {
            step.beforeStep(areaId);
            step.step(areaId);
            step.afterStep(areaId);
        }
    }

    public long getRunnerId() {
        return runnerId;
    }
}
