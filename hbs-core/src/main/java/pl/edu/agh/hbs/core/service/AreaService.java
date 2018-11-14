package pl.edu.agh.hbs.core.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.hbs.core.model.domain.Area;
import pl.edu.agh.hbs.core.model.domain.AreaBordersDefinition;
import pl.edu.agh.hbs.model.Vector;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AreaService {

    public Optional<String> getAreaIdByPosition(Map<String, AreaBordersDefinition> bordersDefinitions,
                                                Vector position) {
        List<String> matchingAreaIds = bordersDefinitions
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().isInside(position))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        if (matchingAreaIds.size() > 1) {
            throw new IllegalStateException("More then one areas found for a given position: "
                    + Arrays.toString(matchingAreaIds.toArray()));
        } else {
            return matchingAreaIds.isEmpty() ? Optional.empty() : Optional.of(matchingAreaIds.get(0));
        }
    }

    public Collection<String> findNeighbourAreas(Collection<? extends Area> areas, Area currentArea) {
        Collection<String> neighbourAreas = new LinkedList<>();
        areas.forEach(area -> {
            if (!area.equals(currentArea)) {
                final double bottomLeftX = area.getBottomLeftCornerPosition().get(0);
                final double bottomLeftY = area.getBottomLeftCornerPosition().get(1);
                final double upperRightX = area.getUpperRightCornerPosition().get(0);
                final double upperRightY = area.getUpperRightCornerPosition().get(1);
                final double currentBottomLeftX = currentArea.getBottomLeftCornerPosition().get(0);
                final double currentBottomLeftY = currentArea.getBottomLeftCornerPosition().get(1);
                final double currentUpperRightX = currentArea.getUpperRightCornerPosition().get(0);
                final double currentUpperRightY = currentArea.getUpperRightCornerPosition().get(1);

                final boolean isSharedTopEdge = currentUpperRightY == bottomLeftY
                        && currentUpperRightX > bottomLeftX
                        && currentBottomLeftX < upperRightX;

                final boolean isSharedBottomEdge = currentBottomLeftY == upperRightY
                        && currentUpperRightX > bottomLeftX
                        && currentBottomLeftX < upperRightX;

                final boolean isSharedLeftEdge = currentBottomLeftX == upperRightX
                        && currentUpperRightY > bottomLeftY
                        && currentBottomLeftY < upperRightY;

                final boolean isSharedRightEdge = currentUpperRightX == bottomLeftX
                        && currentUpperRightY > bottomLeftY
                        && currentBottomLeftY < upperRightY;

                if (isSharedTopEdge || isSharedBottomEdge || isSharedLeftEdge || isSharedRightEdge) {
                    neighbourAreas.add(area.getAreaId());
                }
            }
        });
        return neighbourAreas;
    }
}
