package pl.edu.agh.hbs.model.skill

import pl.edu.agh.hbs.model.perception.{AllPerception, Perception}
import pl.edu.agh.hbs.model.propagation.{AllPropagation, Propagation}
import pl.edu.agh.hbs.model.{Agent, ModifierBuffer}

abstract class Message(val propagation: Propagation = new AllPropagation(),
                       val perception: Seq[Perception] = Seq(new AllPerception())) extends Serializable {

  def shouldProcess(agent: Agent): Boolean = perception.forall(p => p.shouldProcess(agent))

  def process(modifiers: ModifierBuffer): Unit

}
