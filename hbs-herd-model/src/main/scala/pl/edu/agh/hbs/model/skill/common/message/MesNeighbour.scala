package pl.edu.agh.hbs.model.skill.common.message

import pl.edu.agh.hbs.model.perception.{AllPerception, Perception}
import pl.edu.agh.hbs.model.propagation.Propagation
import pl.edu.agh.hbs.model.skill.Message
import pl.edu.agh.hbs.model.skill.common.modifier.ModNeighbour
import pl.edu.agh.hbs.model.{ModifierBuffer, SpeciesObject, Vector}

class MesNeighbour(override val propagation: Propagation,
                   val senderId: String,
                   val species: SpeciesObject,
                   val position: Vector,
                   val velocity: Vector,
                   override val perception: Seq[Perception] = Seq(new AllPerception())
                  ) extends Message(propagation, perception) {

  def process(modifiers: ModifierBuffer): Unit = {
    modifiers.update(ModNeighbour(senderId, species, position, velocity))
  }

}
