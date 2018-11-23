package pl.edu.agh.hbs.simulation.agent

import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.skill.flocking.FlockingAgent
import pl.edu.agh.hbs.model.{Agent, ModifierBuffer}

class WolfAgent(private val initModifiers: Seq[Modifier], inheritedModifiers: ModifierBuffer)
  extends Agent(initModifiers, inheritedModifiers)
    with FlockingAgent {
}
