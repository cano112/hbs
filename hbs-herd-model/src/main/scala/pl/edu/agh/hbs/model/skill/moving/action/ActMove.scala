package pl.edu.agh.hbs.model.skill.moving.action

import pl.edu.agh.hbs.model
import pl.edu.agh.hbs.model.propagation.CircularPropagation
import pl.edu.agh.hbs.model.skill.Action
import pl.edu.agh.hbs.model.skill.basic.modifier._
import pl.edu.agh.hbs.model.skill.common.message.MesNeighbour
import pl.edu.agh.hbs.model.skill.common.modifier.{ModPropagationRadius, ModVelocity}
import pl.edu.agh.hbs.model.{ModifierBuffer, StepOutput}

import scala.collection.mutable.ListBuffer

object ActMove extends Action {

  override def stepsDuration: Int = 1

  override def action(modifiers: ModifierBuffer): StepOutput = {
    val velocity = modifiers.getAll[ModVelocity].map(m => m.velocity).reduce((m, acc) => m + acc)
    val oldPosition = modifiers.getFirst[ModPosition].position
    var position = oldPosition + (velocity / 10)
    val species = modifiers.getFirst[ModSpecies].species
    position = model.Vector((position(0) + 2000 * 5) % 2000, (position(1) + 1500 * 5) % 1500)
    modifiers.update(ModPosition(position))

    val propagationRadius = modifiers.getFirst[ModPropagationRadius].radius
    val agentId = modifiers.getFirst[ModIdentifier].id

    new StepOutput(ListBuffer(new MesNeighbour(new CircularPropagation(position, 1000), agentId, species, position, velocity)))
  }
}
