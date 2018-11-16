package pl.edu.agh.hbs.core.bootstrap;

import com.google.common.util.concurrent.ListenableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.age.compute.api.ThreadPool;
import pl.edu.agh.hbs.core.api.SimulationMap;
import pl.edu.agh.hbs.core.ui.websocket.SimulationWebSocketServer;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Simulation entry-point - runnable used by Age 3 engine.
 * The purpose of this class is to invoke simulation configurer {@link SimulationTopologyConfigurer} and then
 * run {@link StepRunner} for each area configured.
 */
public class HerdBehaviourSimulationRunner implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(HerdBehaviourSimulationRunner.class);

    private final ThreadPool threadPool;
    private final SimulationTopologyConfigurer simulationTopologyConfigurer;
    private final SimulationWebSocketServer webSocketServer;
    private final SimulationMap map;

    @Inject
    public HerdBehaviourSimulationRunner(final ThreadPool threadPool,
                                         final SimulationTopologyConfigurer simulationTopologyConfigurer,
                                         final SimulationWebSocketServer webSocketServer,
                                         final SimulationMap map) {
        this.threadPool = checkNotNull(threadPool);
        this.simulationTopologyConfigurer = checkNotNull(simulationTopologyConfigurer);
        this.webSocketServer = checkNotNull(webSocketServer);
        this.map = checkNotNull(map);
    }

    @Override
    public void run() {
        final List<StepRunner> stepRunners = simulationTopologyConfigurer.configure(map);
        log.info("Runners set: " + stepRunners.size());

        threadPool.submitAll(Collections.singletonList(webSocketServer));
        final List<ListenableFuture<?>> futures = threadPool.submitAll(stepRunners);

        futures.forEach(f -> {
            try {
                f.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace(); // TODO: better handling
            }
        });
    }
}
