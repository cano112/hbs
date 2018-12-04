package pl.edu.agh.hbs.model.skill.breeding.action

import pl.edu.agh.hbs.model.propagation.CircularPropagation
import pl.edu.agh.hbs.model.skill.Action
import pl.edu.agh.hbs.model.skill.basic.modifier._
import pl.edu.agh.hbs.model.skill.breeding.modifier.ModBreedParameters
import pl.edu.agh.hbs.model.skill.common.message.MesNeighbour
import pl.edu.agh.hbs.model.skill.common.modifier.{ModTimer, ModVelocity}
import pl.edu.agh.hbs.model.{ModifierBuffer, StepOutput}

import scala.collection.mutable.ListBuffer

object ActBreed extends Action {

  override def stepsDuration: Int = 1

  override def action(modifiers: ModifierBuffer): StepOutput = {
    modifiers.update(ModTimer(0, "breed"))

    val species = modifiers.getFirst[ModSpecies].species
    val radius = modifiers.getFirst[ModBreedParameters].propagationRadius

    val child = species.newAgent(Seq(), modifiers)
    val childModifiers = child.modifiers
    val childVelocity = childModifiers.getFirst[ModVelocity]("standard").velocity
    val childPosition = childModifiers.getFirst[ModPosition].position
    val childId = childModifiers.getFirst[ModIdentifier].id
    val childSpecies = childModifiers.getFirst[ModSpecies].species

    new StepOutput(
      messages = ListBuffer(new MesNeighbour(new CircularPropagation(childPosition, radius), childId, childSpecies, childPosition, childVelocity)),
      agents = ListBuffer(child))
  }
}
