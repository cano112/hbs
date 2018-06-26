package pl.edu.agh.hbs.core.runners;

import com.google.common.util.concurrent.ListenableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.age.compute.api.ThreadPool;
import pl.edu.agh.hbs.core.model.SimulationMap;
import pl.edu.agh.hbs.core.providers.impl.SimulationPropertiesProvider;
import pl.edu.agh.hbs.core.topology.SimulationTopologyConfigurer;
import pl.edu.agh.hbs.core.websocket.SimulationWebSocketServer;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.google.common.base.Preconditions.checkNotNull;

public class HerdBehaviourSimulationRunner implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(HerdBehaviourSimulationRunner.class);

    private final ThreadPool threadPool;
    private final SimulationTopologyConfigurer simulationTopologyConfigurer;
    private final SimulationPropertiesProvider propertiesProvider;
    private final SimulationWebSocketServer webSocketServer;
    private final SimulationMap map;


    @Inject
    public HerdBehaviourSimulationRunner(final ThreadPool threadPool,
                                         final SimulationTopologyConfigurer simulationTopologyConfigurer,
                                         final SimulationPropertiesProvider propertiesProvider,
                                         final SimulationWebSocketServer webSocketServer,
                                         final SimulationMap map) {
        this.threadPool = checkNotNull(threadPool);
        this.simulationTopologyConfigurer = checkNotNull(simulationTopologyConfigurer);
        this.propertiesProvider = checkNotNull(propertiesProvider);
        this.webSocketServer = checkNotNull(webSocketServer);
        this.map = checkNotNull(map);
    }

    @Override
    public void run() {
        List<StepRunner> stepRunners = simulationTopologyConfigurer.configure(map);
        log.info("Runners set: " + stepRunners.size());

        threadPool.submitAll(Collections.singletonList(webSocketServer));
        final List<ListenableFuture<?>> futures = threadPool.submitAll(stepRunners);

        futures.forEach(f -> {
            try {
                f.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }
}
