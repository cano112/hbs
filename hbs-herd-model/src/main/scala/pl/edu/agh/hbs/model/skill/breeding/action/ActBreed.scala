package pl.edu.agh.hbs.model.skill.breeding.action

import pl.edu.agh.hbs.model.propagation.CircularPropagation
import pl.edu.agh.hbs.model.skill.basic.modifier._
import pl.edu.agh.hbs.model.skill.common.message.MesNeighbour
import pl.edu.agh.hbs.model.skill.common.modifier.{ModActionParameters, ModVelocity}
import pl.edu.agh.hbs.model.skill.{Action, Modifier}
import pl.edu.agh.hbs.model.{ModifierBuffer, StepOutput, Vector}

import scala.collection.mutable.ListBuffer

object ActBreed extends Action {

  override def stepsDuration: Int = 1

  override def action(modifiers: ModifierBuffer): StepOutput = {
    val species = modifiers.getFirst[ModSpecies]

    val initModifiers = ListBuffer.empty[Modifier]
    initModifiers += modifiers.getFirst[ModPosition].copy()
    initModifiers += species.copy()
    modifiers.getAll[ModActionParameters].foreach(m => initModifiers += m.copy())
    initModifiers += ModVelocity(Vector(), "standard")
    initModifiers += ModIdentifier(species.species.nextId())
    initModifiers += modifiers.getFirst[ModRepresentation].copy()

    val child = species.species.newAgent(Seq())
    child.initialize(child.parametersCopiedForChild(modifiers) ++ initModifiers)
    val childModifiers = child.modifiers
    val childVelocity = childModifiers.getFirst[ModVelocity]("standard").velocity
    val childPosition = childModifiers.getFirst[ModPosition].position
    val childId = childModifiers.getFirst[ModIdentifier].id
    val childSpecies = childModifiers.getFirst[ModSpecies].species

    new StepOutput(
      messages = ListBuffer(new MesNeighbour(new CircularPropagation(childPosition, 1000), childId, childSpecies, childPosition, childVelocity)),
      agents = ListBuffer(child))
  }
}
