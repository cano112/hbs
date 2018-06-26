package pl.edu.agh.hbs.core.topology;

import com.hazelcast.core.IMap;
import com.hazelcast.core.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.age.compute.api.DistributionUtilities;
import pl.edu.agh.age.compute.api.TopologyProvider;
import pl.edu.agh.age.compute.api.UnicastMessenger;
import pl.edu.agh.age.compute.api.WorkerAddress;
import pl.edu.agh.age.compute.api.topology.Topology;
import pl.edu.agh.hbs.core.model.Area;
import pl.edu.agh.hbs.core.model.SimulationMap;
import pl.edu.agh.hbs.core.runners.StepRunner;
import pl.edu.agh.hbs.core.stop_condition.StopCondition;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class SimulationTopologyConfigurer {

    private final TopologyProvider<Long> topologyProvider;
    private final Topology<? extends Serializable> topology;
    private final UnicastMessenger unicastMessenger;
    private final StopCondition stopCondition;

    private final IdGenerator idGenerator;
    private final IMap<Long, WorkerAddress> areaLocations;
    private final IMap<String, Integer> stepsCount;
    private final IMap<String, Area> areas;

    @Autowired
    @SuppressWarnings("unchecked")
    public SimulationTopologyConfigurer(final TopologyProvider<?> topologyProvider,
                                        final Topology<? extends Serializable> topology,
                                        final UnicastMessenger unicastMessenger,
                                        final DistributionUtilities distributionUtilities,
                                        final StopCondition stopCondition) {
        this.topologyProvider = (TopologyProvider<Long>) checkNotNull(topologyProvider);
        this.topology = checkNotNull(topology);
        this.unicastMessenger = checkNotNull(unicastMessenger);
        this.stopCondition = checkNotNull(stopCondition);
        this.idGenerator = distributionUtilities.getIdGenerator("area");
        this.areaLocations = distributionUtilities.getMap("area-locations");
        this.stepsCount = distributionUtilities.getMap("steps-count");
        this.areas = distributionUtilities.getMap("area-models");
    }

    public List<StepRunner> configure(SimulationMap simulationMap) {
        topologyProvider.setTopology(this.topology());

        simulationMap.getAreas().forEach(area -> {
            stepsCount.put(area.getAreaId(), 0);
            areas.put(area.getAreaId(), area);
        });

        List<StepRunner> stepRunners = simulationMap.getAreas()
                .stream()
                .map(area -> new StepRunner(area.getStep(), stopCondition, idGenerator.newId(), area.getAreaId()))
                .collect(Collectors.toList());

        stepRunners.forEach(runner -> areaLocations.put(runner.getRunnerId(), unicastMessenger.address()));
        topologyProvider.addNodes(stepRunners
                .stream()
                .map(StepRunner::getRunnerId)
                .collect(Collectors.toSet()));
        return stepRunners;
    }

    @SuppressWarnings("unchecked")
    public <T extends Serializable> Topology<T> topology() {
        return (Topology<T>) topology;
    }
}
