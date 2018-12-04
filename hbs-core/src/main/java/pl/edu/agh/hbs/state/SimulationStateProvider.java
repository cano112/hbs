package pl.edu.agh.hbs.state;

import pl.edu.agh.age.compute.api.WorkerAddress;
import pl.edu.agh.hbs.model.Agent;
import pl.edu.agh.hbs.simulation.api.Area;
import pl.edu.agh.hbs.simulation.api.AreaBordersDefinition;

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
     * Acquire lock for a given area
     *
     * @param areaId area identifier
     */
    void lockArea(String areaId);

    /**
     * Unlock given area
     *
     * @param areaId area identifier
     */
    void unlockArea(String areaId);

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
     * Set number of areas in distributed map - for quicker access
     *
     * @param count number of areas
     */
    void addAreasCount(int count);

    /**
     * Areas are synchronized in two stages: 'step' and 'after step'. The synchronization point
     * is after each stage - areas are concurrent only inside a given stage. For synchronization,
     * we use countdown latches.
     * <p>
     * We fill the latch with a given number of tokens, which are removed when an area finishes its stage.
     * When the latch is empty, we fill the next latch ('step' -> 'after step').
     * {@link pl.edu.agh.hbs.bootstrap.StepRunner} waits until the latchs contains at least one token and then
     * invokes next stage.
     *
     * @param stage stage of step execution
     */
    void setStepLatchCount(AreaStepStage stage);

    /**
     * Add a given number of tokens to latch
     *
     * @param stage stage of step execution
     * @param count number of tokens
     */
    void addToStepLatch(AreaStepStage stage, int count);

    /**
     * Get token from a bucket for a given stage
     *
     * @param stage stage of step execution
     * @see SimulationStateProvider#setStepLatchCount(AreaStepStage)
     */
    void getToken(AreaStepStage stage);

    /**
     * Check if token for a given stage is available
     *
     * @param stage stage of step execution
     * @return true if available, false otherwise
     */
    boolean isTokenAvailable(AreaStepStage stage);
}
