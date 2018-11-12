package pl.edu.agh.hbs.model.skill.dying.message

import pl.edu.agh.hbs.model.Agent
import pl.edu.agh.hbs.model.propagation.Propagation
import pl.edu.agh.hbs.model.skill.Message
import pl.edu.agh.hbs.model.skill.common.modifier.ModNeighbour

class MesIsDead(override val propagation: Propagation,
                val senderId: String) extends Message(propagation) {

  def process(agent: Agent): Unit = {
    agent.modifiers -- agent.modifiers.getAll[ModNeighbour](Seq(senderId))
  }
}
