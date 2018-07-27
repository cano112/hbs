package pl.edu.agh.hbs.model.skill

import pl.edu.agh.hbs.model.Agent
import pl.edu.agh.hbs.model.propagation.Propagation

abstract class Message(val propagation: Propagation) extends Serializable {

  def process(agent: Agent): Unit

}
