package pl.edu.agh.hbs.simulation.generic;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.edu.agh.hbs.api.Area;
import pl.edu.agh.hbs.api.MessageDistributionStrategy;
import pl.edu.agh.hbs.model.Agent;
import pl.edu.agh.hbs.model.skill.Message;
import pl.edu.agh.hbs.utils.ScalaInterop;
import scala.collection.Seq;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Profile("neighbourMessageDistribution")
public class NeighbourMessageDistributionStrategy implements MessageDistributionStrategy {

    @Override
    public Map<String, Collection<Message>> distributeToAreas(Area area, Collection<Message> messages) {
        Map<String, Collection<Message>> distribution = new HashMap<>();
        area.getNeighbourAreas().forEach(areaId -> distribution.put(areaId, messages));
        distribution.put(area.getAreaId(), messages);
        return distribution;
    }

    @Override
    public Seq<Message> filterForAgent(Agent agent, Collection<Message> messages) {
        return ScalaInterop.toSeq(messages);
    }
}
