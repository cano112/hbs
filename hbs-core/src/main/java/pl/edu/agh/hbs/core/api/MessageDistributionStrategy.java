package pl.edu.agh.hbs.core.api;

import pl.edu.agh.hbs.model.Agent;
import pl.edu.agh.hbs.model.skill.Message;
import scala.collection.Seq;

import java.util.Collection;
import java.util.Map;

/**
 * Strategy for distributing messages to areas and agents.
 */
public interface MessageDistributionStrategy {

    /**
     * Method invoked when messages are distributed to areas.
     *
     * @param area     message-source area
     * @param messages messages to distribute
     * @return map area identifer -> collections of messages to pass
     */
    Map<String, Collection<Message>> distributeToAreas(Area area, Collection<Message> messages);

    /**
     * Filter out messages which should not be received by a given agent.
     *
     * @param agent    agent
     * @param messages messages before filter
     * @return feltered messages
     */
    Seq<Message> filterForAgent(Agent agent, Collection<Message> messages);
}
