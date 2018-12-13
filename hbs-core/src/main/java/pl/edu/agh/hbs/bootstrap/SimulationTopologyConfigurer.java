package pl.edu.agh.hbs.bootstrap;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pl.edu.agh.hbs.simulation.api.Area;
import pl.edu.agh.hbs.simulation.api.SimulationMap;
import pl.edu.agh.hbs.simulation.api.StopCondition;
import pl.edu.agh.hbs.state.AreaStateService;
import pl.edu.agh.hbs.state.SimulationStateProvider;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class SimulationTopologyConfigurer {

    private final StopCondition stopCondition;
    private final SimulationStateProvider stateProvider;
    private final BeanFactory beanFactory;
    private final AreaStateService areaStateService;
    private final SimulationMap simulationMap;

    @Autowired
    @Lazy
    @SuppressWarnings("unchecked")
    public SimulationTopologyConfigurer(
            final StopCondition stopCondition,
            final SimulationStateProvider stateProvider,
            final BeanFactory beanFactory,
            final AreaStateService areaStateService,
            final SimulationMap map) {

        this.stopCondition = checkNotNull(stopCondition);
        this.stateProvider = checkNotNull(stateProvider);
        this.beanFactory = checkNotNull(beanFactory);
        this.areaStateService = checkNotNull(areaStateService);
        this.simulationMap = checkNotNull(map);
    }

    public List<StepRunner> configure(int nodesCount) {
        int nodeOffset = stateProvider.addNode();

        List<? extends Area> areas = simulationMap.getAreas();
        List<StepRunner> stepRunners = new LinkedList<>();

        for (int i = nodeOffset; i < areas.size(); i += nodesCount) {
            final Area area = areas.get(i);
            final Collection<String> neighbourAreas = areaStateService
                    .findNeighbourAreaIds(simulationMap.getAreas(), area);
            area.addNeigbourAreas(neighbourAreas);
            final String areaId = area.getAreaId();
            stateProvider.initializeStepsNumber(areaId);
            stateProvider.setAreaById(areaId, area);
            stateProvider.setAreaBorderByAreaId(areaId, area.getAreaBordersDefinition());

            final StepRunner stepRunner = (StepRunner) beanFactory.getBean("stepRunner", stateProvider,
                    area.getStep(), stopCondition, area.getAreaId());
            stepRunners.add(stepRunner);
        }
        return stepRunners;
    }
}
