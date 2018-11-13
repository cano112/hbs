package pl.edu.agh.hbs.core.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import pl.edu.agh.hbs.core.model.domain.AreaBordersDefinition;
import pl.edu.agh.hbs.core.model.domain.cartesian.CartesianRectangularBordersDefinition;
import pl.edu.agh.hbs.model.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

@RunWith(MockitoJUnitRunner.class)
public class AreaServiceTest {

    @InjectMocks
    private AreaService areaService;

    @Test
    public void shouldReturnAreaWhenMatchingAreaProvided() {
        // given
        final Map<String, AreaBordersDefinition> areaBorders = new HashMap<>();
        areaBorders.put("area-1",
                new CartesianRectangularBordersDefinition(Vector.of(0, 0), Vector.of(100, 100),
                        true, true, true, true));
        areaBorders.put("area-2",
                new CartesianRectangularBordersDefinition(Vector.of(100, 0), Vector.of(200, 200),
                        false, true, true, true));

        // when
        Optional<String> area = areaService.getAreaIdByPosition(areaBorders, Vector.of(50, 50));

        // then
        then(area).contains("area-1");
    }

    @Test
    public void shouldThrowExceptionWhenAreasOverlap() {
        // given
        final Map<String, AreaBordersDefinition> areaBorders = new HashMap<>();
        areaBorders.put("area-1",
                new CartesianRectangularBordersDefinition(Vector.of(0, 0), Vector.of(100, 100),
                        true, true, true, true));
        areaBorders.put("area-2",
                new CartesianRectangularBordersDefinition(Vector.of(0, 0), Vector.of(200, 200),
                        false, true, true, true));

        // expected
        thenThrownBy(() -> areaService.getAreaIdByPosition(areaBorders, Vector.of(50, 50)))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void shouldReturnEmptyOptionalWhenNoMatchingAreaFound() {
        // given
        final Map<String, AreaBordersDefinition> areaBorders = new HashMap<>();
        areaBorders.put("area-1",
                new CartesianRectangularBordersDefinition(Vector.of(0, 0), Vector.of(100, 100),
                        true, true, true, true));
        areaBorders.put("area-2",
                new CartesianRectangularBordersDefinition(Vector.of(100, 0), Vector.of(200, 200),
                        false, true, true, true));

        // when
        Optional<String> area = areaService.getAreaIdByPosition(areaBorders, Vector.of(300, 300));

        // then
        then(area).isEmpty();
    }

}