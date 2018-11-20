package pl.edu.agh.hbs.simulation.state;

import com.hazelcast.core.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pl.edu.agh.age.compute.api.DistributionUtilities;
import pl.edu.agh.age.compute.api.WorkerAddress;
import pl.edu.agh.hbs.api.Area;
import pl.edu.agh.hbs.api.AreaBordersDefinition;
import pl.edu.agh.hbs.api.SimulationStateProvider;
import pl.edu.agh.hbs.model.Agent;
import pl.edu.agh.hbs.simulation.state.events.listeners.AreaAddedEventListener;
import pl.edu.agh.hbs.simulation.state.events.listeners.AreaStepBucketEventListener;

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

    private static final Logger log = LoggerFactory.getLogger(SimulationStateProviderImpl.class);

    private final IMap<String, WorkerAddress> areaLocations;

    private final IMap<String, Integer> areaStepsCount;

    private final IMap<String, Area> areas;

    private final IMap<String, AreaBordersDefinition> areaBorders;

    private final IMap<AreaStepStage, Integer> areaStepBuckets;

    private final IMap<String, Integer> constants;

    @Autowired
    public SimulationStateProviderImpl(
            final DistributionUtilities distributionUtilities,
            final AreaStepBucketEventListener areaStepBucketEventListener,
            final AreaAddedEventListener areaAddedEventListener) {

        checkNotNull(distributionUtilities);
        checkNotNull(areaAddedEventListener);

        areaStepsCount = distributionUtilities.getMap("steps-count");
        areas = distributionUtilities.getMap("area-models");
        areaLocations = distributionUtilities.getMap("area-locations");
        areaBorders = distributionUtilities.getMap("area-borders");
        constants = distributionUtilities.getMap("constants");
        areaStepBuckets = distributionUtilities.getMap("area-step-buckets");
        areaStepBuckets.set(AreaStepStage.BEFORE_STEP, 0);
        areaStepBuckets.set(AreaStepStage.STEP, 0);
        areaStepBuckets.set(AreaStepStage.AFTER_STEP, 0);

        areas.addEntryListener(areaAddedEventListener, true);
        areaStepBuckets.addEntryListener(areaStepBucketEventListener, true);
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
    public void setAreasCount(int count) {
        constants.set("areasCount", count);
    }

    @Override
    public void fillBucket(AreaStepStage stage, int count) {
        areaStepBuckets.set(stage, count);
    }

    @Override
    public void fillBucket(AreaStepStage stage) {
        areaStepBuckets.set(stage, areaBorders.size());
    }

    @Override
    public void getToken(AreaStepStage stage) {
        areaStepBuckets.lock(stage);
        try {
            final int current = areaStepBuckets.get(stage);
            if (current == 0) {
                throw new IllegalStateException("No token available");
            }
            areaStepBuckets.set(stage, current - 1);
        } finally {
            areaStepBuckets.unlock(stage);
        }
    }

    @Override
    public boolean isTokenAvailable(AreaStepStage stage) {
        return areaStepBuckets.get(stage) > 0;
    }
}
