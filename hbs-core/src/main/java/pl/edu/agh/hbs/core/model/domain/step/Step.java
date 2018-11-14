package pl.edu.agh.hbs.core.model.domain.step;

import java.io.Serializable;

/**
 * Area step definition.
 */
public interface Step extends Serializable {

    /**
     * Action invoked before main step execution procedure.
     *
     * @param areaId area identifier
     */
    void beforeStep(String areaId);

    /**
     * Main step execution procedure.
     * @param areaId area identifier
     */
    void step(String areaId);

    /**
     * Action invoked sfter main step execution procedure.
     * @param areaId area identifier
     */
    void afterStep(String areaId);
}
