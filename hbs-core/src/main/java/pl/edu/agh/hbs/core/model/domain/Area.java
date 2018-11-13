package pl.edu.agh.hbs.core.model.domain;

import pl.edu.agh.hbs.core.model.domain.step.Step;
import pl.edu.agh.hbs.model.Agent;
import pl.edu.agh.hbs.model.Vector;
import pl.edu.agh.hbs.model.skill.Message;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Area implements Serializable {

    private final String areaId;
    private final AreaBordersDefinition areaBordersDefinition;
    private final Collection<Agent> agents;
    private final Collection<Message> messages;
    private transient final Step step;

    public Area(String areaId, Step step, AreaBordersDefinition areaBordersDefinition, Collection<Agent> agents) {
        this.areaId = areaId;
        this.step = step;
        this.agents = Collections.newSetFromMap(new ConcurrentHashMap<>());
        this.messages = Collections.newSetFromMap(new ConcurrentHashMap<>());
        this.areaBordersDefinition = areaBordersDefinition;

        this.agents.addAll(agents);
    }

    public String getAreaId() {
        return areaId;
    }

    public Collection<Agent> getAgents() {
        return agents;
    }

    public void addAgent(Agent agent) {
        this.agents.add(agent);
    }

    public void removeAgent(Agent agent) {
        this.agents.remove(agent);
    }

    public Step getStep() {
        return step;
    }

    public void addMessages(Collection<Message> messages) {
        this.messages.addAll(messages);
    }

    public void clearMessages() {
        this.messages.clear();
    }

    public Collection<Message> getMessages() {
        return Collections.unmodifiableCollection(messages);
    }

    public AreaBordersDefinition getAreaBordersDefinition() {
        return areaBordersDefinition;
    }

    public boolean isInside(Vector position) {
        return this.areaBordersDefinition.isInside(position);
    }
}
