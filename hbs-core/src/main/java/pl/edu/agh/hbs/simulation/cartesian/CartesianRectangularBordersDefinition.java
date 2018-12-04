package pl.edu.agh.hbs.simulation.cartesian;

import pl.edu.agh.hbs.model.Vector;
import pl.edu.agh.hbs.simulation.api.AreaBordersDefinition;

public class CartesianRectangularBordersDefinition implements AreaBordersDefinition {

    private final Vector bottomLeftPosition;
    private final Vector upperRightPosition;

    private final boolean leftBorderInclusive;
    private final boolean rightBorderInclusive;
    private final boolean topBorderInclusive;
    private final boolean bottomBorderInclusive;

    public CartesianRectangularBordersDefinition(
            final Vector bottomLeftPosition,
            final Vector upperRightPosition,
            final boolean leftBorderInclusive,
            final boolean rightBorderInclusive,
            final boolean topBorderInclusive,
            final boolean bottomBorderInclusive) {
        this.bottomLeftPosition = bottomLeftPosition;
        this.upperRightPosition = upperRightPosition;
        this.leftBorderInclusive = leftBorderInclusive;
        this.rightBorderInclusive = rightBorderInclusive;
        this.topBorderInclusive = topBorderInclusive;
        this.bottomBorderInclusive = bottomBorderInclusive;
    }

    @Override
    public boolean isInside(Vector Vector) {
        double bottomLeftVectorArray[] = this.bottomLeftPosition.toArray();
        double upperRightVectorArray[] = this.upperRightPosition.toArray();
        double currentVectorArray[] = Vector.toArray();

        if (hasNotSameSize(bottomLeftVectorArray, upperRightVectorArray, currentVectorArray)) {
            throw new IllegalArgumentException("Vectors has not the same dimensions");
        }

        for (int i = 0; i < bottomLeftVectorArray.length; i++) {
            if ((currentVectorArray[i] < bottomLeftVectorArray[i])
                    || (i == 0 && !leftBorderInclusive && currentVectorArray[i] == bottomLeftVectorArray[i])
                    || (i == 1 && !bottomBorderInclusive && currentVectorArray[i] == bottomLeftVectorArray[i])
                    || (currentVectorArray[i] > upperRightVectorArray[i])
                    || (i == 0 && !rightBorderInclusive && currentVectorArray[i] == upperRightVectorArray[i])
                    || (i == 1 && !topBorderInclusive && currentVectorArray[i] == upperRightVectorArray[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Vector getBottomLeftPosition() {
        return bottomLeftPosition;
    }

    @Override
    public Vector getUpperRightPosition() {
        return upperRightPosition;
    }

    private boolean hasNotSameSize(double arrayA[], double arrayB[], double arrayC[]) {
        return !(arrayA.length == arrayB.length && arrayB.length == arrayC.length);
    }
}
