package pl.edu.agh.hbs.model.skill

import pl.edu.agh.hbs.model.Agent
import pl.edu.agh.hbs.model.perception.{Perception, SimplePerception}
import pl.edu.agh.hbs.model.propagation.Propagation

abstract class Message(val propagation: Propagation, val perception: Seq[Perception] = Seq(new SimplePerception())) extends Serializable {

  def process(agent: Agent): Unit

}
