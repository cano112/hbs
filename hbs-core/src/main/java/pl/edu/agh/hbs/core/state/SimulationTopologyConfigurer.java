package pl.edu.agh.hbs.core.state;

import com.google.common.eventbus.EventBus;
import com.hazelcast.core.IdGenerator;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.age.compute.api.DistributionUtilities;
import pl.edu.agh.age.compute.api.TopologyProvider;
import pl.edu.agh.age.compute.api.UnicastMessenger;
import pl.edu.agh.age.compute.api.topology.Topology;
import pl.edu.agh.hbs.core.model.domain.SimulationMap;
import pl.edu.agh.hbs.core.state.SimulationStateProvider;
import pl.edu.agh.hbs.core.runners.StepRunner;
import pl.edu.agh.hbs.core.model.domain.stop.StopCondition;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class SimulationTopologyConfigurer {

    private final TopologyProvider<String> topologyProvider;
    private final Topology<? extends Serializable> topology;
    private final UnicastMessenger unicastMessenger;
    private final StopCondition stopCondition;
    private final IdGenerator idGenerator;
    private final SimulationStateProvider stateProvider;
    private final EventBus eventBus;
    private final BeanFactory beanFactory;

    @Autowired
    @SuppressWarnings("unchecked")
    public SimulationTopologyConfigurer(
            final TopologyProvider<?> topologyProvider,
            final Topology<? extends Serializable> topology,
            final UnicastMessenger unicastMessenger,
            final DistributionUtilities distributionUtilities,
            final StopCondition stopCondition,
            final SimulationStateProvider stateProvider,
            final EventBus eventBus,
            final BeanFactory beanFactory) {

        checkNotNull(distributionUtilities);
        this.topologyProvider = (TopologyProvider<String>) checkNotNull(topologyProvider);
        this.topology = checkNotNull(topology);
        this.unicastMessenger = checkNotNull(unicastMessenger);
        this.stopCondition = checkNotNull(stopCondition);
        this.stateProvider = checkNotNull(stateProvider);
        this.idGenerator = distributionUtilities.getIdGenerator("area");
        this.eventBus = checkNotNull(eventBus);
        this.beanFactory = checkNotNull(beanFactory);
    }

    public List<StepRunner> configure(SimulationMap simulationMap) {
        topologyProvider.setTopology(this.topology());

        simulationMap.getAreas().forEach(area -> {
            stateProvider.initializeStepsNumber(area.getAreaId());
            stateProvider.setAreaById(area.getAreaId(), area);
            stateProvider.setAreaBorderByAreaId(area.getAreaId(), area.getAreaBordersDefinition());
        });

        List<StepRunner> stepRunners = simulationMap.getAreas()
                .stream()
                .map(area -> (StepRunner) beanFactory.getBean("stepRunner",
                                eventBus, area.getStep(), stopCondition, idGenerator.newId(), area.getAreaId()))
                .collect(Collectors.toList());

        stepRunners.forEach(runner ->
                stateProvider.setAreaLocationByAreaId(runner.getAreaId(), unicastMessenger.address()));
        topologyProvider.addNodes(stepRunners
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
