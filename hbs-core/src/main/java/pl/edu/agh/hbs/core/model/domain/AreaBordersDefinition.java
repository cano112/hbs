package pl.edu.agh.hbs.core.model.domain;

import pl.edu.agh.hbs.model.Vector;

import java.io.Serializable;

/**
 * Model for area borders.
 */
public interface AreaBordersDefinition extends Serializable {
    /**
     * Check if given vector is inside borders.
     *
     * @param Vector vector to check
     * @return true if inside area, false otherwise
     */
    boolean isInside(Vector Vector);

    /**
     * @return area's bottom left corner position vector
     */
    Vector getBottomLeftPosition();

    /**
     * @return area's upper right corner position vector
     */
    Vector getUpperRightPosition();
}
