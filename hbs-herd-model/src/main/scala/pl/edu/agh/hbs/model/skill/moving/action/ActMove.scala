package pl.edu.agh.hbs.model.skill.moving.action

import pl.edu.agh.hbs.model
import pl.edu.agh.hbs.model.perception.CircularPerception
import pl.edu.agh.hbs.model.propagation.CircularPropagation
import pl.edu.agh.hbs.model.skill.Action
import pl.edu.agh.hbs.model.skill.basic.modifier._
import pl.edu.agh.hbs.model.skill.common.message.MesNeighbour
import pl.edu.agh.hbs.model.skill.common.modifier.ModVelocity
import pl.edu.agh.hbs.model.skill.moving.modifier.ModMoveParameters
import pl.edu.agh.hbs.model.{ModifierBuffer, StepOutput}

import scala.collection.mutable.ListBuffer

object ActMove extends Action {

  override def stepsDuration: Int = 1

  override def action(modifiers: ModifierBuffer): StepOutput = {
    val velocity = modifiers.getFirst[ModVelocity]("standard").velocity
    val oldPosition = modifiers.getFirst[ModPosition].position
    val species = modifiers.getFirst[ModSpecies].species
    val agentId = modifiers.getFirst[ModIdentifier].id
    val velocityFactor = modifiers.getFirst[ModMoveParameters].velocityFactor
    val propagationRadius = modifiers.getFirst[ModMoveParameters].propagationRadius

    var position = oldPosition + (velocity * velocityFactor)

    //bounding position todo
    position = model.Vector((position(0) + 2000 * 5) % 2000, (position(1) + 1500 * 5) % 1500)
    modifiers.update(ModPosition(position))

    new StepOutput(
      ListBuffer(new MesNeighbour(new CircularPropagation(position, propagationRadius), agentId, species, position, velocity, Seq(new CircularPerception(propagationRadius + 50, position)))))
  }
}
