package pl.edu.agh.hbs.core.api;

/**
 * Interface for simulation stop condition. When condition is fulfilled, the simulation should finish.
 */
public interface StopCondition {

    /**
     * Check if condition has been reached.
     *
     * @param id area identifier for which the condition is checked
     * @return true if condition fulfilled, false otherwise
     */
    boolean isReached(String id);
}
