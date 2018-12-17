package pl.edu.agh.hbs.state;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import pl.edu.agh.hbs.model.Vector;
import pl.edu.agh.hbs.simulation.api.Area;
import pl.edu.agh.hbs.simulation.api.AreaBordersDefinition;
import pl.edu.agh.hbs.simulation.cartesian.CartesianRectangularBordersDefinition;
import pl.edu.agh.hbs.simulation.generic.GenericArea;

import java.util.*;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

@RunWith(MockitoJUnitRunner.class)
public class AreaStateServiceTest {

    @InjectMocks
    private AreaStateService areaStateService;

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
        Optional<String> area = areaStateService.getAreaIdByPosition(areaBorders, Vector.of(50, 50));

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
        thenThrownBy(() -> areaStateService.getAreaIdByPosition(areaBorders, Vector.of(50, 50)))
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
        Optional<String> area = areaStateService.getAreaIdByPosition(areaBorders, Vector.of(300, 300));

        // then
        then(area).isEmpty();
    }

    @Test
    public void shouldReturnAreaWithSharedBorderWhenBottomBorderShared() {
        // given
        final String matchingAreaId = "area-3";
        final Area currentArea = getCurrentArea("area-1");
        final Collection<Area> areas = Arrays.asList(
                getNotMatchingArea("area-2"),
                getSharedBottomBorderArea(matchingAreaId));

        // when
        final Collection<String> neighbourAreaIds = areaStateService.findNeighbourAreaIds(areas, currentArea);

        // then
        then(neighbourAreaIds).containsOnly(matchingAreaId);
    }

    @Test
    public void shouldReturnAreaWithSharedBorderWhenTopBorderShared() {
        // given
        final String matchingAreaId = "area-3";
        final Area currentArea = getCurrentArea("area-1");
        final Collection<Area> areas = Arrays.asList(
                getNotMatchingArea("area-2"),
                getSharedTopBorderArea("area-3"));

        // when
        final Collection<String> neighbourAreaIds = areaStateService.findNeighbourAreaIds(areas, currentArea);

        // then
        then(neighbourAreaIds).containsOnly(matchingAreaId);
    }

    @Test
    public void shouldReturnAreaWithSharedBorderWhenLeftBorderShared() {
        // given
        final String matchingAreaId = "area-3";
        final Area currentArea = getCurrentArea("area-1");
        final Collection<Area> areas = Arrays.asList(
                getNotMatchingArea("area-2"),
                getSharedLeftBorderArea("area-3"));

        // when
        final Collection<String> neighbourAreaIds = areaStateService.findNeighbourAreaIds(areas, currentArea);

        // then
        then(neighbourAreaIds).containsOnly(matchingAreaId);
    }

    @Test
    public void shouldReturnAreaWithSharedBorderWhenRightBorderShared() {
        // given
        final String matchingAreaId = "area-3";
        final Area currentArea = getCurrentArea("area-1");
        final Collection<Area> areas = Arrays.asList(
                getNotMatchingArea("area-2"),
                getSharedRightBorderArea("area-3"));

        // when
        final Collection<String> neighbourAreaIds = areaStateService.findNeighbourAreaIds(areas, currentArea);

        // then
        then(neighbourAreaIds).containsOnly(matchingAreaId);
    }

    @Test
    public void shouldReturnEmptyCollectionWhenMatchedWithItself() {
        // given
        final Area currentArea = getCurrentArea("area-1");
        final Collection<Area> areas = Arrays.asList(getCurrentArea("area-1"), getCurrentArea("area-1"));

        // when
        final Collection<String> neighbourAreaIds = areaStateService.findNeighbourAreaIds(areas, currentArea);

        // then
        then(neighbourAreaIds).isEmpty();
    }

    @Test
    public void shouldReturnEmptyCollectionWhenNoAreaProvided() {
        // given
        final Area currentArea = getCurrentArea("area-1");

        // when
        final Collection<String> neighbourAreaIds = areaStateService
                .findNeighbourAreaIds(Collections.emptyList(), currentArea);

        // then
        then(neighbourAreaIds).isEmpty();
    }

    private Area getCurrentArea(String areaId) {
        final AreaBordersDefinition currentAreaBordersDefinition = new CartesianRectangularBordersDefinition(
                Vector.of(50, 50),
                Vector.of(100, 100),
                false,
                false,
                false,
                true);
        return new GenericArea(areaId, null, currentAreaBordersDefinition, new LinkedList<>());
    }

    private Area getNotMatchingArea(String areaId) {
        final AreaBordersDefinition areaBordersDefinition = new CartesianRectangularBordersDefinition(
                Vector.of(200, 200),
                Vector.of(300, 300),
                false,
                false,
                false,
                true);
        return new GenericArea(areaId, null, areaBordersDefinition, new LinkedList<>());
    }

    private Area getSharedBottomBorderArea(String areaId) {
        final AreaBordersDefinition areaBordersDefinition = new CartesianRectangularBordersDefinition(
                Vector.of(50, 0),
                Vector.of(100, 50),
                false,
                false,
                true,
                true);
        return new GenericArea(areaId, null, areaBordersDefinition, new LinkedList<>());
    }

    private Area getSharedTopBorderArea(String areaId) {
        final AreaBordersDefinition areaBordersDefinition = new CartesianRectangularBordersDefinition(
                Vector.of(50, 100),
                Vector.of(100, 200),
                false,
                false,
                true,
                true);
        return new GenericArea(areaId, null, areaBordersDefinition, new LinkedList<>());
    }

    private Area getSharedLeftBorderArea(String areaId) {
        final AreaBordersDefinition areaBordersDefinition = new CartesianRectangularBordersDefinition(
                Vector.of(0, 50),
                Vector.of(50, 100),
                false,
                false,
                true,
                true);
        return new GenericArea(areaId, null, areaBordersDefinition, new LinkedList<>());
    }

    private Area getSharedRightBorderArea(String areaId) {
        final AreaBordersDefinition areaBordersDefinition = new CartesianRectangularBordersDefinition(
                Vector.of(100, 50),
                Vector.of(150, 100),
                false,
                false,
                true,
                true);
        return new GenericArea(areaId, null, areaBordersDefinition, new LinkedList<>());
    }

}