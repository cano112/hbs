package pl.edu.agh.hbs.core.simulation.generic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.edu.agh.hbs.core.api.Area;
import pl.edu.agh.hbs.core.api.MessageDistributionStrategy;
import pl.edu.agh.hbs.core.api.SimulationStateProvider;
import pl.edu.agh.hbs.core.utils.ScalaInterop;
import pl.edu.agh.hbs.model.Agent;
import pl.edu.agh.hbs.model.Vector;
import pl.edu.agh.hbs.model.skill.Message;
import scala.collection.Seq;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
@Profile({"default", "propagationMessageDistribution"})
public class PropagationMessageDistributionStrategy implements MessageDistributionStrategy {

    private final SimulationStateProvider stateProvider;

    @Autowired
    public PropagationMessageDistributionStrategy(SimulationStateProvider stateProvider) {
        this.stateProvider = checkNotNull(stateProvider);
    }

    @Override
    public Map<String, Collection<Message>> distributeToAreas(Area area, Collection<Message> messages) {
        Map<String, Collection<Message>> distribution = new HashMap<>();
        distribution.put(area.getAreaId(), messages);

        stateProvider.getAreaBorderDefinitions().forEach((targetAreaId, borderDefinition) -> {
            Collection<Message> areaMessages = new LinkedList<>();
            messages.forEach(message -> {
                final Vector leftBottomPosition = borderDefinition.getBottomLeftPosition();
                final Vector rightUpperPosition = borderDefinition.getUpperRightPosition();
                if (message.propagation().shouldSend(leftBottomPosition, rightUpperPosition)) {
                    areaMessages.add(message);
                }
            });
            distribution.put(targetAreaId, areaMessages);
        });

        return distribution;
    }

    @Override
    public Seq<Message> filterForAgent(Agent agent, Collection<Message> messages) {
        Collection<Message> filteredMessages = messages
                .stream()
                .filter(message -> message.propagation().shouldReceive(agent))
                .collect(Collectors.toList());
        return ScalaInterop.toSeq(filteredMessages);
    }
}
