package pl.edu.agh.hbs.state;

import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pl.edu.agh.age.compute.api.DistributionUtilities;
import pl.edu.agh.age.compute.api.WorkerAddress;
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

    private final IMap<String, WorkerAddress> areaLocations;

    private final IMap<String, Integer> areaStepsCount;

    private final IMap<String, Area> areas;

    private final IMap<String, AreaBordersDefinition> areaBorders;

    private final IMap<AreaStepStage, Integer> areaStepLatches;

    private final IMap<ConstantLabel, Integer> constants;

    @Autowired
    public SimulationStateProviderImpl(
            final DistributionUtilities distributionUtilities,
            final AreaStepLatchEventListener areaStepLatchEventListener,
            final AreaAddedEventListener areaAddedEventListener) {

        checkNotNull(distributionUtilities);
        checkNotNull(areaAddedEventListener);

        areaStepsCount = distributionUtilities.getMap("steps-count");
        areas = distributionUtilities.getMap("area-models");
        areaLocations = distributionUtilities.getMap("area-locations");
        areaBorders = distributionUtilities.getMap("area-borders");
        constants = distributionUtilities.getMap("constants");
        areaStepLatches = distributionUtilities.getMap("area-step-buckets");

        areaStepLatches.putIfAbsent(AreaStepStage.STEP, 0);
        areaStepLatches.putIfAbsent(AreaStepStage.AFTER_STEP, 0);
        constants.putIfAbsent(ConstantLabel.AREAS_COUNT, 0);

        areas.addEntryListener(areaAddedEventListener, true);
        areaStepLatches.addEntryListener(areaStepLatchEventListener, true);
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
    public WorkerAddress getAreaLocationByAreaId(String areaId) {
        return areaLocations.get(areaId);
    }

    @Override
    public void setAreaLocationByAreaId(String areaId, WorkerAddress address) {
        areaLocations.set(areaId, address);
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
    public void addAreasCount(int count) {
        try {
            constants.lock(ConstantLabel.AREAS_COUNT);
            final int currentCount = constants.get(ConstantLabel.AREAS_COUNT);
            constants.set(ConstantLabel.AREAS_COUNT, currentCount + count);
        } finally {
            constants.unlock(ConstantLabel.AREAS_COUNT);
        }
    }

    @Override
    public void setStepLatchCount(AreaStepStage stage) {
        areaStepLatches.set(stage, constants.get(ConstantLabel.AREAS_COUNT));
    }

    @Override
    public void addToStepLatch(AreaStepStage stage, int count) {
        try {
            areaStepLatches.lock(stage);
            final int currentCount = areaStepLatches.get(stage);
            areaStepLatches.set(stage, currentCount + count);
        } finally {
            areaStepLatches.unlock(stage);
        }
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
}
