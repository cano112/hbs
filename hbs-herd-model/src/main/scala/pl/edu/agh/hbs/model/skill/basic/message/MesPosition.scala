package pl.edu.agh.hbs.model.skill.basic.message

import pl.edu.agh.hbs.model.{Agent, Position}
import pl.edu.agh.hbs.model.propagation.Propagation
import pl.edu.agh.hbs.model.skill.Message
import pl.edu.agh.hbs.model.skill.basic.modifier.ModPosition

class MesPosition(override val propagation: Propagation, val position: Position) extends Message(propagation) {

  def process(agent: Agent): Unit = {
    if (propagation.shouldReceive(agent)) {
      val modPosition = agent.modifiers.collect { case a: ModPosition => a }
      if (modPosition.nonEmpty)
        agent.modifiers -= modPosition.head
      agent.modifiers += new ModPosition(this.position)
    }
  }

}
