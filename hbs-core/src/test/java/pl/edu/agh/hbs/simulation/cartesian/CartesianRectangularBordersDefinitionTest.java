package pl.edu.agh.hbs.simulation.cartesian;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import pl.edu.agh.hbs.model.Vector;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(MockitoJUnitRunner.class)
public class CartesianRectangularBordersDefinitionTest {

    @Test
    public void shouldBeInsideWhenFullyInside() {
        // given
        final CartesianRectangularBordersDefinition bordersDefinition =
                new CartesianRectangularBordersDefinition(Vector.of(0, 0), Vector.of(100, 100),
                        false, false, false, false);

        // when
        final boolean isInside = bordersDefinition.isInside(Vector.of(50, 50));

        // then
        then(isInside).isTrue();
    }

    @Test
    public void shouldBeInsideWhenOnLeftBorderAndLeftBorderIsInclusive() {
        // given
        final CartesianRectangularBordersDefinition bordersDefinition =
                new CartesianRectangularBordersDefinition(Vector.of(0, 0), Vector.of(100, 100),
                        true, false, false, false);

        // when
        final boolean isInside = bordersDefinition.isInside(Vector.of(0, 30));

        // then
        then(isInside).isTrue();
    }

    @Test
    public void shouldBeOutsideWhenOnLeftBorderAndLeftBorderIsExclusive() {
        // given
        final CartesianRectangularBordersDefinition bordersDefinition =
                new CartesianRectangularBordersDefinition(Vector.of(0, 0), Vector.of(100, 100),
                        false, false, false, false);

        // when
        final boolean isInside = bordersDefinition.isInside(Vector.of(0, 30));

        // then
        then(isInside).isFalse();
    }

    @Test
    public void shouldBeOutsideWhenOnCornerAndBordersAreExclusive() {
        // given
        final CartesianRectangularBordersDefinition bordersDefinition =
                new CartesianRectangularBordersDefinition(Vector.of(0, 0), Vector.of(100, 100),
                        false, false, false, false);

        // when
        final boolean isInside = bordersDefinition.isInside(Vector.of(100, 100));

        // then
        then(isInside).isFalse();
    }

    @Test
    public void shouldBeInsideWhenOnTopBorderAndTopBorderInclusive() {
        // given
        final CartesianRectangularBordersDefinition bordersDefinition =
                new CartesianRectangularBordersDefinition(Vector.of(0, 0), Vector.of(100, 100),
                        false, false, true, false);

        // when
        final boolean isInside = bordersDefinition.isInside(Vector.of(50, 100));

        // then
        then(isInside).isTrue();
    }
}