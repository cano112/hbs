package pl.edu.agh.hbs.core.model;

import pl.edu.agh.hbs.core.steps.Step;
import pl.edu.agh.hbs.model.Agent;
import pl.edu.agh.hbs.model.skill.Message;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class Area implements Serializable {

    private final String areaId;
    private final List<Agent> agents;
    private final List<Message> messages;
    private transient final Step step;

    public Area(String areaId, Step step) {
        this.areaId = areaId;
        this.step = step;
        this.agents = new LinkedList<>();
        this.messages = new LinkedList<>();
    }

    public Area(String areaId, Step step, List<Agent> agents) {
        this.areaId = areaId;
        this.step = step;
        this.agents = agents;
        this.messages = new LinkedList<>();
    }

    public String getAreaId() {
        return areaId;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public void addAgent(Agent agent) {
        this.agents.add(agent);
    }

    public Step getStep() {
        return step;
    }

    public void addMessages(List<Message> messages) {
        this.messages.addAll(messages);
    }

    public void clearMessages() {
        this.messages.clear();
    }

    public List<Message> getMessages() {
        return Collections.unmodifiableList(messages);
    }
}
