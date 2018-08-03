package pl.edu.agh.hbs.model.skill.basic.message

import pl.edu.agh.hbs.core.providers.Representation
import pl.edu.agh.hbs.model.Agent
import pl.edu.agh.hbs.model.propagation.Propagation
import pl.edu.agh.hbs.model.skill.{Message, Modifier}
import pl.edu.agh.hbs.model.skill.basic.modifier.ModRepresentation

class MesRepresentation(override val propagation: Propagation,
                        override val senderId: String,
                        val representation: Representation) extends Message(propagation, senderId) {

  def process(agent: Agent): Unit = {
    if (propagation.shouldReceive(agent)) {
      val modRepresentation = Modifier.getAll[ModRepresentation](agent.modifiers)
      if (modRepresentation.nonEmpty)
        agent.modifiers -= modRepresentation.head
      agent.modifiers += ModRepresentation(this.representation)
    }
  }

}
