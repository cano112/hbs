package pl.edu.agh.hbs.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.age.compute.api.ThreadPool;
import pl.edu.agh.hbs.model.HerdUnit;

import javax.inject.Inject;

public class HerdBehaviourSimulationRunner implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(HerdBehaviourSimulationRunner.class);

    private final ThreadPool threadPool;
    private final HerdUnit unit;

    @Inject
    public HerdBehaviourSimulationRunner(ThreadPool threadPool, HerdUnit unit) {
        this.threadPool = threadPool;
        this.unit = unit;
    }

    @Override
    public void run() {
        unit.printHello();
    }
}
