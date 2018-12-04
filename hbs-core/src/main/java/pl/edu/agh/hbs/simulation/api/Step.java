package pl.edu.agh.hbs.simulation.api;

import java.io.Serializable;

/**
 * Area step definition.
 */
public interface Step extends Serializable {

    /**
     * Main step execution procedure.
     *
     * @param areaId area identifier
     */
    void step(String areaId);

    /**
     * Action invoked sfter main step execution procedure.
     *
     * @param areaId area identifier
     */
    void afterStep(String areaId);
}
