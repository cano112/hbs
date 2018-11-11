package pl.edu.agh.hbs.simulation.agent

import pl.edu.agh.hbs.model.Agent
import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.skill.flocking.FlockingAgent

class HumanAgent(private val initModifiers: Seq[Modifier]) extends Agent(initModifiers) with FlockingAgent {
}
