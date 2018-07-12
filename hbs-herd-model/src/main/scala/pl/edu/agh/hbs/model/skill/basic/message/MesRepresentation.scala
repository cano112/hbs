package pl.edu.agh.hbs.model.skill.basic.message

import pl.edu.agh.hbs.model.Agent
import pl.edu.agh.hbs.model.propagation.Propagation
import pl.edu.agh.hbs.model.representation.Representation
import pl.edu.agh.hbs.model.skill.Message
import pl.edu.agh.hbs.model.skill.basic.modifier.ModRepresentation

class MesRepresentation(override val propagation: Propagation, val representation: Representation) extends Message(propagation) {

  def process(agent: Agent): Unit = {
    if (propagation.shouldReceive(agent)) {
      val modRepresentation = agent.modifiers.collect { case a: ModRepresentation => a }
      if (modRepresentation.nonEmpty)
        agent.modifiers -= modRepresentation.head
      agent.modifiers += new ModRepresentation(this.representation)
    }
  }

}
