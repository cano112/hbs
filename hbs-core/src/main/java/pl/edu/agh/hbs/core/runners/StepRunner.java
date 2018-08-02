package pl.edu.agh.hbs.core.runners;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.edu.agh.hbs.core.event_listeners.EventListener;
import pl.edu.agh.hbs.core.model.events.AreaStepsSynchronizedEvent;
import pl.edu.agh.hbs.core.steps.Step;
import pl.edu.agh.hbs.core.stop_condition.StopCondition;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
@Scope("prototype")
public class StepRunner extends EventListener implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(StepRunner.class);

    private final long runnerId;
    private final String areaId;
    private final Step step;
    private final StopCondition stopCondition;
    private final Object lock;
    private Boolean areasSynchronized;


    public StepRunner(final EventBus eventBus,
                      final Step step,
                      final StopCondition stopCondition,
                      final long runnerId,
                      final String areaId) {
        super(eventBus);
        this.step = checkNotNull(step);
        this.stopCondition = checkNotNull(stopCondition);
        this.runnerId = runnerId;
        this.areaId = areaId;
        this.areasSynchronized = true;
        this.lock = new Object();
    }

    @Override
    public void run() {
        while(true) {
            synchronized (lock) {
                if(areasSynchronized) {
                    if(stopCondition.isReached(areaId)) {
                        log.info("Stop condition reached for area: {}", areaId);
                        break;
                    }
                    areasSynchronized = false;
                    step.beforeStep(areaId);
                    step.step(areaId);
                    step.afterStep(areaId);
                }
            }
        }
    }

    @Subscribe
    public void onAreaStepsSynchronized(AreaStepsSynchronizedEvent event) {
        log.debug("Steps synchronized");
        synchronized (lock) {
            areasSynchronized = true;
        }
    }

    public long getRunnerId() {
        return runnerId;
    }

    public String getAreaId() {
        return areaId;
    }
}
