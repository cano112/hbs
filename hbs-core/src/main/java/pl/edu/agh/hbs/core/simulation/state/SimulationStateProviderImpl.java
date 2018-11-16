package pl.edu.agh.hbs.core.simulation.state;

import com.hazelcast.aggregation.Aggregators;
import com.hazelcast.core.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.age.compute.api.DistributionUtilities;
import pl.edu.agh.age.compute.api.WorkerAddress;
import pl.edu.agh.hbs.core.api.Area;
import pl.edu.agh.hbs.core.api.AreaBordersDefinition;
import pl.edu.agh.hbs.core.api.SimulationStateProvider;
import pl.edu.agh.hbs.core.simulation.state.events.listeners.AreaAddedEventListener;
import pl.edu.agh.hbs.core.simulation.state.events.listeners.AreasSynchronizedUpdateEventListener;
import pl.edu.agh.hbs.model.Agent;

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
public class SimulationStateProviderImpl implements SimulationStateProvider {

    private static final Logger log = LoggerFactory.getLogger(SimulationStateProviderImpl.class);

    /**
     * Maps area identifier to node's address on which area is running.
     */
    private final IMap<String, WorkerAddress> areaLocations;

    /**
     * Maps area identifier to the number of steps done for a given area.
     * Used for synchronization between areas. Extracted from base area holder for better performance.
     */
    private final IMap<String, Integer> areaStepsCount;

    /**
     * Base simulation state holder. Maps area identifier to area's current state.
     */
    private final IMap<String, Area> areas;

    /**
     * Maps area identifier to area's border definition. Extracted from base area holder for better performance.
     */
    private final IMap<String, AreaBordersDefinition> areaBorders;

    /**
     * Global simulation state flags.
     */
    private final IMap<StateFlag, Boolean> stateFlags;

    @Autowired
    public SimulationStateProviderImpl(
            final DistributionUtilities distributionUtilities,
            final AreaAddedEventListener areaAddedEventListener,
            final AreasSynchronizedUpdateEventListener areasSynchronizedUpdateEventListener) {

        checkNotNull(distributionUtilities);
        checkNotNull(areaAddedEventListener);
        checkNotNull(areasSynchronizedUpdateEventListener);

        areaStepsCount = distributionUtilities.getMap("steps-count");
        areas = distributionUtilities.getMap("area-models");
        areaLocations = distributionUtilities.getMap("area-locations");
        stateFlags = distributionUtilities.getMap("state-flags");
        areaBorders = distributionUtilities.getMap("area-borders");

        stateFlags.put(StateFlag.TIMER_EXPIRED, false);

        areas.addEntryListener(areaAddedEventListener, true);
        stateFlags.addEntryListener(areasSynchronizedUpdateEventListener,
                (Map.Entry<StateFlag, Boolean> entry) -> entry.getKey() == StateFlag.AREAS_SYNCHRONIZED,
                true);
    }

    @Override
    public int getStepsNumber(String areaId) {
        return areaStepsCount.get(areaId);
    }

    @Override
    public void incrementStepsNumber(String areaId) {
        areaStepsCount.lock(areaId);
        final int currentVal = areaStepsCount.get(areaId);
        areaStepsCount.set(areaId, currentVal + 1);
        areaStepsCount.unlock(areaId);
        afterStepsCountUpdate();
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
    public void expireTimer() {
        this.stateFlags.set(StateFlag.TIMER_EXPIRED, true);
    }

    @Override
    public boolean hasTimerExpired() {
        return this.stateFlags.get(StateFlag.TIMER_EXPIRED);
    }

    private void afterStepsCountUpdate() {
        stateFlags.put(StateFlag.AREAS_SYNCHRONIZED, isEachAreaStepEqual());
    }

    private boolean isEachAreaStepEqual() {
        return areaStepsCount.aggregate(Aggregators.distinct()).size() <= 1;
    }
}
