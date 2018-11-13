package pl.edu.agh.hbs.core.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.hbs.core.model.domain.AreaBordersDefinition;
import pl.edu.agh.hbs.model.Vector;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

        if(matchingAreaIds.size() > 1) {
            throw new IllegalStateException("More then one areas found for a given position: "
                    + Arrays.toString(matchingAreaIds.toArray()));
        } else {
            return matchingAreaIds.isEmpty() ? Optional.empty() : Optional.of(matchingAreaIds.get(0));
        }
    }
}
