package pl.edu.agh.hbs.core.model.domain;

import pl.edu.agh.hbs.model.Vector;

import java.io.Serializable;

/**
 * Model for area borders.
 */
public interface AreaBordersDefinition extends Serializable {
    /**
     * Checks if given vector is inside borders.
     * @param Vector vector to check
     * @return true if inside area, false otherwise
     */
    boolean isInside(Vector Vector);
}
