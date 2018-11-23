package pl.edu.agh.hbs.model.skill.dying.message

import pl.edu.agh.hbs.model.Agent
import pl.edu.agh.hbs.model.propagation.AllPropagation
import pl.edu.agh.hbs.model.skill.Message
import pl.edu.agh.hbs.model.skill.common.modifier.ModNeighbour

class MesIsDead(val senderId: String) extends Message(new AllPropagation()) {

  def process(agent: Agent): Unit = {
    agent.modifiers -- agent.modifiers.getAll[ModNeighbour](Seq(senderId)) // works even if empty
  }
}
