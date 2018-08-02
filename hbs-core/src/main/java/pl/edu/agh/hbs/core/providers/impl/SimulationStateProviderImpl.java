package pl.edu.agh.hbs.core.providers.impl;

import com.hazelcast.aggregation.Aggregators;
import com.hazelcast.core.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.age.compute.api.DistributionUtilities;
import pl.edu.agh.age.compute.api.WorkerAddress;
import pl.edu.agh.hbs.core.event_listeners.state_listeners.AreaAddedEventListener;
import pl.edu.agh.hbs.core.event_listeners.state_listeners.AreasSynchronizedUpdateEventListener;
import pl.edu.agh.hbs.core.model.Area;
import pl.edu.agh.hbs.core.model.StateFlag;
import pl.edu.agh.hbs.core.providers.SimulationStateProvider;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

@Service("simulationStateProvider")
public class SimulationStateProviderImpl implements SimulationStateProvider {

    private static final Logger log = LoggerFactory.getLogger(SimulationStateProviderImpl.class);

    private final IMap<String, WorkerAddress> areaLocations;
    private final IMap<String, Integer> areaStepsCount;
    private final IMap<String, Area> areas;
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

    private void afterStepsCountUpdate() {
        stateFlags.put(StateFlag.AREAS_SYNCHRONIZED, isEachAreaStepEqual());
    }

    private boolean isEachAreaStepEqual() {
        return areaStepsCount.aggregate(Aggregators.distinct()).size() <= 1;
    }
}
