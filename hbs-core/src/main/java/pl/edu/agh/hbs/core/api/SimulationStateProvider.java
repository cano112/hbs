package pl.edu.agh.hbs.core.api;

import pl.edu.agh.age.compute.api.WorkerAddress;
import pl.edu.agh.hbs.model.Agent;

import java.util.Collection;
import java.util.Map;

/**
 * Interface for global simulation state.
 */
public interface SimulationStateProvider {

    /**
     * Retrieve number of steps completed for a given area.
     *
     * @param areaId area identifier
     * @return number of steps completed
     */
    int getStepsNumber(String areaId);

    /**
     * Increment completed steps number for a given area. Usually invoked after step completion.
     *
     * @param areaId area identifier
     */
    void incrementStepsNumber(String areaId);

    /**
     * Initialize completed steps number for a given area. Invoked at simulation setup.
     *
     * @param areaId area identifier
     */
    void initializeStepsNumber(String areaId);

    /**
     * Retrieve area data for a given area
     *
     * @param areaId area identifer
     * @return area current state
     */
    Area getAreaById(String areaId);

    /**
     * Set area state in simulation's global state.
     * Usually invoked after updating area's current state, as {@link com.hazelcast.core.IMap} read operations are not
     * backed by distributed map.
     *
     * @param areaId area identifer
     * @param area   area updated model
     */
    void setAreaById(String areaId, Area area);

    /**
     * Retrieve address of the node, where given area is being run.
     *
     * @param areaId area identifier
     * @return node address
     */
    WorkerAddress getAreaLocationByAreaId(String areaId);

    /**
     * Set node address for a given area. Invoked at simulation setup.
     *
     * @param areaId  area identifier
     * @param address node address
     */
    void setAreaLocationByAreaId(String areaId, WorkerAddress address);

    /**
     * Get collection of area identifiers mapped to border definition. Not backed by Hazelcast's distributed map.
     *
     * @return unmodifiable shallow copy of state map with border definitions
     */
    Map<String, AreaBordersDefinition> getAreaBorderDefinitions();

    /**
     * Set border definition for a given area. Invoked on simulation setup.
     *
     * @param areaId            area identifier
     * @param bordersDefinition border definition
     */
    void setAreaBorderByAreaId(String areaId, AreaBordersDefinition bordersDefinition);

    /**
     * @return collection of agents from all areas.
     */
    Collection<Agent> getAllAgents();

    /**
     * Set timer state to expired
     */
    void expireTimer();

    /**
     * Check timer state
     *
     * @return true if timer expired, false otherwise
     */
    boolean hasTimerExpired();
}
