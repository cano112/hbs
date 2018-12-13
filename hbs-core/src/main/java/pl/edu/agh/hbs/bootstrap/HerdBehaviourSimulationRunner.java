package pl.edu.agh.hbs.bootstrap;

import com.google.common.util.concurrent.ListenableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import pl.edu.agh.age.compute.api.ThreadPool;
import pl.edu.agh.hbs.exceptions.SimulationExecutionException;
import pl.edu.agh.hbs.state.AreaStepStage;
import pl.edu.agh.hbs.state.SimulationStateProvider;
import pl.edu.agh.hbs.ui.websocket.SimulationWebSocketServer;
import pl.edu.agh.hbs.utils.PropertiesUtils;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Simulation entry-point - {@link Runnable} implementation used by Age 3 engine.
 * The purpose of this class is to invoke simulation configurer {@link SimulationTopologyConfigurer} and then
 * run {@link StepRunner} for each area configured.
 */
public class HerdBehaviourSimulationRunner implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(HerdBehaviourSimulationRunner.class);

    private static final String NODES_COUNT_PROPERTIES_KEY = "hbs.simulation.nodes";

    private final ThreadPool threadPool;
    private final SimulationWebSocketServer webSocketServer;
    private final SimulationStateProvider stateProvider;
    private final SimulationTopologyConfigurer topologyConfigurer;
    private final Properties properties;

    @Autowired
    public HerdBehaviourSimulationRunner(final ThreadPool threadPool,
                                         final SimulationTopologyConfigurer topologyConfigurer,
                                         final SimulationWebSocketServer webSocketServer,
                                         final SimulationStateProvider stateProvider,
                                         @Qualifier("simulationProperties") final Properties simulationProperties) {
        this.threadPool = checkNotNull(threadPool);
        this.webSocketServer = checkNotNull(webSocketServer);
        this.stateProvider = checkNotNull(stateProvider);
        this.topologyConfigurer = checkNotNull(topologyConfigurer);
        this.properties = checkNotNull(simulationProperties);
    }

    @Override
    public void run() {
        int nodesCount = PropertiesUtils.getIntegerValue(properties, NODES_COUNT_PROPERTIES_KEY);
        List<StepRunner> stepRunners = topologyConfigurer.configure(nodesCount);
        log.info("Runners set: " + stepRunners.size());

        while (stateProvider.getNodesCount() != nodesCount) ;
        if (stateProvider.isMasterNode()) {
            stateProvider.setStepLatchCount(AreaStepStage.STEP);
            threadPool.submitAll(Collections.singletonList(webSocketServer));
        }

        final List<ListenableFuture<?>> futures = threadPool.submitAll(stepRunners);

        futures.forEach(f -> {
            try {
                f.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new SimulationExecutionException(e);
            }
        });
    }
}
