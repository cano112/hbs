package pl.edu.agh.hbs.core.model.domain.cartesian;

import pl.edu.agh.hbs.core.model.domain.AreaBordersDefinition;
import pl.edu.agh.hbs.model.Vector;

public class CartesianRectangularBordersDefinition implements AreaBordersDefinition {

    private final Vector bottomLeftVector;
    private final Vector upperRightVector;

    private final boolean leftBorderInclusive;
    private final boolean rightBorderInclusive;
    private final boolean topBorderInclusive;
    private final boolean bottomBorderInclusive;

    public CartesianRectangularBordersDefinition(
            Vector bottomLeftVector,
            Vector upperRightVector,
            boolean leftBorderInclusive,
            boolean rightBorderInclusive,
            boolean topBorderInclusive,
            boolean bottomBorderInclusive) {
        this.bottomLeftVector = bottomLeftVector;
        this.upperRightVector = upperRightVector;
        this.leftBorderInclusive = leftBorderInclusive;
        this.rightBorderInclusive = rightBorderInclusive;
        this.topBorderInclusive = topBorderInclusive;
        this.bottomBorderInclusive = bottomBorderInclusive;
    }

    @Override
    public boolean isInside(Vector Vector) {
        int bottomLeftVectorArray[] = this.bottomLeftVector.toArray();
        int upperRightVectorArray[] = this.upperRightVector.toArray();
        int currentVectorArray[] = Vector.toArray();

        if(hasNotSameSize(bottomLeftVectorArray, upperRightVectorArray, currentVectorArray)) {
            throw new IllegalArgumentException("Vectors has not the same dimensions");
        }

        for(int i = 0; i < bottomLeftVectorArray.length; i++) {
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

    private boolean hasNotSameSize(int arrayA[], int arrayB[], int arrayC[]) {
        return !(arrayA.length == arrayB.length && arrayB.length == arrayC.length);
    }
}
