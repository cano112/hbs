package pl.edu.agh.hbs.model.skill.common.message

import pl.edu.agh.hbs.model.perception.{Perception, SimplePerception}
import pl.edu.agh.hbs.model.propagation.Propagation
import pl.edu.agh.hbs.model.skill.Message
import pl.edu.agh.hbs.model.skill.common.modifier.ModNeighbour
import pl.edu.agh.hbs.model.{Agent, SpeciesObject, Vector}

class MesNeighbour(override val propagation: Propagation,
                   val senderId: String,
                   val species: SpeciesObject,
                   val position: Vector,
                   val velocity: Vector,
                   override val perception: Seq[Perception] = Seq(new SimplePerception())
                  ) extends Message(propagation, perception) {

  def process(agent: Agent): Unit = {
    if (perception.forall(p => p.shouldProcess(agent))) {
      agent.modifiers.update(ModNeighbour(senderId, species, position, velocity))
    }
  }
}
