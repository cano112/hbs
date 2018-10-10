package pl.edu.agh.hbs.simulation

import pl.edu.agh.hbs.model.Agent
import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.skill.move.FlockingAgent

class Human(private val initModifiers: Seq[Modifier]) extends Agent(initModifiers) with FlockingAgent {
}
