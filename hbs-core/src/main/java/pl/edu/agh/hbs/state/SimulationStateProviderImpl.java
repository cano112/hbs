package pl.edu.agh.hbs.state;

import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pl.edu.agh.age.compute.api.DistributionUtilities;
import pl.edu.agh.hbs.model.Agent;
import pl.edu.agh.hbs.simulation.api.Area;
import pl.edu.agh.hbs.simulation.api.AreaBordersDefinition;
import pl.edu.agh.hbs.state.events.listeners.AreaAddedEventListener;
import pl.edu.agh.hbs.state.events.listeners.AreaStepLatchEventListener;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This class provides access to Hazelcast's distributed maps, which contain global simulation state.
 * The maps are denormalized for quicker read/write operations.
 */
@Service("simulationStateProvider")
@Lazy
public class SimulationStateProviderImpl implements SimulationStateProvider {

    private final IMap<String, Integer> areaStepsCount;

    private final IMap<String, Area> areas;

    private final IMap<String, AreaBordersDefinition> areaBorders;

    private final IMap<AreaStepStage, Integer> areaStepLatches;

    private final IMap<ConstantLabel, Integer> constants;

    private boolean master;

    @Autowired
    public SimulationStateProviderImpl(
            final DistributionUtilities distributionUtilities,
            final AreaStepLatchEventListener areaStepLatchEventListener,
            final AreaAddedEventListener areaAddedEventListener) {

        checkNotNull(distributionUtilities);
        checkNotNull(areaAddedEventListener);

        areaStepsCount = distributionUtilities.getMap("steps-count");
        areas = distributionUtilities.getMap("area-models");
        areaBorders = distributionUtilities.getMap("area-borders");
        constants = distributionUtilities.getMap("constants");
        areaStepLatches = distributionUtilities.getMap("area-step-buckets");

        areaStepLatches.putIfAbsent(AreaStepStage.STEP, 0);
        areaStepLatches.putIfAbsent(AreaStepStage.AFTER_STEP, 0);
        constants.putIfAbsent(ConstantLabel.NODES_COUNT, 0);

        areas.addEntryListener(areaAddedEventListener, true);
        areaStepLatches.addEntryListener(areaStepLatchEventListener, true);

        master = false;
    }

    @Override
    public int getStepsNumber(String areaId) {
        return areaStepsCount.get(areaId);
    }

    @Override
    public void incrementStepsNumber(String areaId) {
        areaStepsCount.lock(areaId);
        try {
            final int currentVal = areaStepsCount.get(areaId);
            areaStepsCount.set(areaId, currentVal + 1);
        } finally {
            areaStepsCount.unlock(areaId);
        }
    }

    @Override
    public void initializeStepsNumber(String areaId) {
        areaStepsCount.put(areaId, 0);
    }

    @Override
    public Area getAreaById(String areaId) {
        return areas.get(areaId);
    }

    @Override
    public void setAreaById(String areaId, Area area) {
        areas.set(areaId, area);
    }

    @Override
    public void lockArea(String areaId) {
        areas.lock(areaId);
    }

    @Override
    public void unlockArea(String areaId) {
        areas.unlock(areaId);
    }

    @Override
    public Map<String, AreaBordersDefinition> getAreaBorderDefinitions() {
        return Collections.unmodifiableMap(new HashMap<>(areaBorders));
    }

    @Override
    public void setAreaBorderByAreaId(String areaId, AreaBordersDefinition bordersDefinition) {
        areaBorders.set(areaId, bordersDefinition);
    }

    @Override
    public Collection<Agent> getAllAgents() {
        return areas.values()
                .stream()
                .flatMap(area -> area.getAgents().stream())
                .collect(Collectors.toSet());
    }

    @Override
    public void setStepLatchCount(AreaStepStage stage) {
        areaStepLatches.set(stage, areas.size());
    }

    @Override
    public void getToken(AreaStepStage stage) {
        areaStepLatches.lock(stage);
        try {
            final int current = areaStepLatches.get(stage);
            if (current == 0) {
                throw new IllegalStateException("No token available");
            }
            areaStepLatches.set(stage, current - 1);
        } finally {
            areaStepLatches.unlock(stage);
        }
    }

    @Override
    public boolean isTokenAvailable(AreaStepStage stage) {
        return areaStepLatches.get(stage) > 0;
    }

    @Override
    public boolean isMasterNode() {
        return master;
    }

    @Override
    public int getNodesCount() {
        return constants.get(ConstantLabel.NODES_COUNT);
    }

    @Override
    public int addNode() {
        try {
            constants.lock(ConstantLabel.NODES_COUNT);
            int currentCount = constants.get(ConstantLabel.NODES_COUNT);
            if (currentCount == 0) {
                master = true;
            }
            constants.set(ConstantLabel.NODES_COUNT, currentCount + 1);
            return currentCount;
        } finally {
            constants.unlock(ConstantLabel.NODES_COUNT);
        }
    }
}
