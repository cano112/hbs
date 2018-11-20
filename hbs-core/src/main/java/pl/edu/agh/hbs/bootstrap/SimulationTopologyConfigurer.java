package pl.edu.agh.hbs.bootstrap;

import com.google.common.eventbus.EventBus;
import com.hazelcast.core.IdGenerator;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.age.compute.api.DistributionUtilities;
import pl.edu.agh.age.compute.api.TopologyProvider;
import pl.edu.agh.age.compute.api.UnicastMessenger;
import pl.edu.agh.age.compute.api.topology.Topology;
import pl.edu.agh.hbs.api.SimulationMap;
import pl.edu.agh.hbs.api.SimulationStateProvider;
import pl.edu.agh.hbs.api.StopCondition;
import pl.edu.agh.hbs.simulation.state.AreaStateService;
import pl.edu.agh.hbs.simulation.state.AreaStepStage;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class SimulationTopologyConfigurer {

    private final TopologyProvider<String> computeTopologyProvider;
    private final Topology<? extends Serializable> topology;
    private final UnicastMessenger unicastMessenger;
    private final StopCondition stopCondition;
    private final IdGenerator idGenerator;
    private final SimulationStateProvider stateProvider;
    private final EventBus eventBus;
    private final BeanFactory beanFactory;
    private final AreaStateService areaStateService;

    @Autowired
    @SuppressWarnings("unchecked")
    public SimulationTopologyConfigurer(
            final TopologyProvider<?> computeTopologyProvider,
            final Topology<? extends Serializable> topology,
            final UnicastMessenger unicastMessenger,
            final DistributionUtilities distributionUtilities,
            final StopCondition stopCondition,
            final SimulationStateProvider stateProvider,
            final EventBus eventBus,
            final BeanFactory beanFactory,
            final AreaStateService areaStateService) {

        checkNotNull(distributionUtilities);
        this.computeTopologyProvider = (TopologyProvider<String>) checkNotNull(computeTopologyProvider);
        this.topology = checkNotNull(topology);
        this.unicastMessenger = checkNotNull(unicastMessenger);
        this.stopCondition = checkNotNull(stopCondition);
        this.stateProvider = checkNotNull(stateProvider);
        this.idGenerator = distributionUtilities.getIdGenerator("area");
        this.eventBus = checkNotNull(eventBus);
        this.beanFactory = checkNotNull(beanFactory);
        this.areaStateService = checkNotNull(areaStateService);
    }

    public List<StepRunner> configure(SimulationMap simulationMap) {
        computeTopologyProvider.setTopology(this.topology());
        stateProvider.setAreasCount(simulationMap.getAreas().size());
        simulationMap.getAreas().forEach(area -> {
            final Collection<String> neighbourAreas = areaStateService
                    .findNeighbourAreaIds(simulationMap.getAreas(), area);
            area.addNeigbourAreas(neighbourAreas);
            final String areaId = area.getAreaId();
            stateProvider.initializeStepsNumber(areaId);
            stateProvider.setAreaById(areaId, area);
            stateProvider.setAreaBorderByAreaId(areaId, area.getAreaBordersDefinition());
        });
        stateProvider.fillBucket(AreaStepStage.BEFORE_STEP, simulationMap.getAreas().size());

        List<StepRunner> stepRunners = simulationMap.getAreas()
                .stream()
                .map(area -> (StepRunner) beanFactory.getBean("stepRunner", stateProvider,
                        area.getStep(), stopCondition, idGenerator.newId(), area.getAreaId()))
                .collect(Collectors.toList());

        stepRunners.forEach(runner ->
                stateProvider.setAreaLocationByAreaId(runner.getAreaId(), unicastMessenger.address()));
        computeTopologyProvider.addNodes(stepRunners
                .stream()
                .map(StepRunner::getAreaId)
                .collect(Collectors.toSet()));
        return stepRunners;
    }

    @SuppressWarnings("unchecked")
    public <T extends Serializable> Topology<T> topology() {
        return (Topology<T>) topology;
    }
}
