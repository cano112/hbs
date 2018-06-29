package pl.edu.agh.hbs.model.skill

import pl.edu.agh.hbs.model.propagation.Propagation
import pl.edu.agh.hbs.model.Agent
import pl.edu.agh.hbs.model.skill.basic.modifier.position.Position

abstract class Message(val position: Position, val propagation: Propagation) extends Serializable {

  def process(agent: Agent): Unit

}
