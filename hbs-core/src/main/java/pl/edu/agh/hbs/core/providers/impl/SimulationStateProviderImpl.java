package pl.edu.agh.hbs.core.providers.impl;

import com.hazelcast.core.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.age.compute.api.DistributionUtilities;
import pl.edu.agh.hbs.core.model.Area;
import pl.edu.agh.hbs.core.providers.SimulationStateProvider;

import static com.google.common.base.Preconditions.checkNotNull;

@Service("simulationStateProvider")
public class SimulationStateProviderImpl implements SimulationStateProvider {

    private static final Logger log = LoggerFactory.getLogger(SimulationStateProviderImpl.class);

    private final IMap<String, Integer> areaStepsCount;
    private final IMap<String, Area> areas;

    @Autowired
    public SimulationStateProviderImpl(final DistributionUtilities distributionUtilities) {
        checkNotNull(distributionUtilities);
        this.areaStepsCount = distributionUtilities.getMap("steps-count");
        this.areas = distributionUtilities.getMap("area-models");
    }

    public int getStepsNumber(String areaId) {
        return areaStepsCount.get(areaId);
    }

    public void incrementStepsNumber(String areaId) {
        int currentVal = areaStepsCount.get(areaId);
        areaStepsCount.put(areaId, currentVal + 1);
    }

    public Area getAreaById(String areaId) {
        return areas.get(areaId);
    }

    public void setAreaById(String areaId, Area area) {
        areas.set(areaId, area);
    }
}
