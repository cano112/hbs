package pl.edu.agh.hbs.core.model;

import pl.edu.agh.hbs.core.steps.Step;
import pl.edu.agh.hbs.model.Agent;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public abstract class Area implements Serializable {

    private final String areaId;
    private final List<Agent> agents;
    private transient final Step step;

    public Area(String areaId, Step step) {
        this.areaId = areaId;
        this.step = step;
        this.agents = new LinkedList<>();
    }

    public Area(String areaId, Step step, List<Agent> agents) {
        this.areaId = areaId;
        this.step = step;
        this.agents = agents;
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
}
