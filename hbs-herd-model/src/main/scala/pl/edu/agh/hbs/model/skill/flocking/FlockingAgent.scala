package pl.edu.agh.hbs.model.skill.flocking

import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.skill.flocking.instantAction.{ActAlignmentVelocity, ActCohesionVelocity, ActSeparationVelocity}
import pl.edu.agh.hbs.model.skill.flocking.modifier.{ModAlignmentVelocityParameters, ModCohesionVelocityParameters, ModSeparationVelocityParameters}
import pl.edu.agh.hbs.model.{Agent, ModifierBuffer}

import scala.collection.mutable.ListBuffer

trait FlockingAgent extends Agent {
  this.beforeStepActions += ActCohesionVelocity
  this.beforeStepActions += ActSeparationVelocity
  this.beforeStepActions += ActAlignmentVelocity

  override def modifiersCopiedFromParent(inherited: ModifierBuffer): Seq[Modifier] = {
    val modifiers = ListBuffer.empty[Modifier]
    inherited.getAll[ModCohesionVelocityParameters].foreach(m => modifiers += m.copy())
    inherited.getAll[ModSeparationVelocityParameters].foreach(m => modifiers += m.copy())
    inherited.getAll[ModAlignmentVelocityParameters].foreach(m => modifiers += m.copy())
    super.modifiersCopiedFromParent(inherited) ++ modifiers
  }

  override def defaultModifiers(): Seq[Modifier] = {
    val modifiers = ListBuffer.empty[Modifier]
    modifiers += ModCohesionVelocityParameters(1 / 7.5)
    modifiers += ModSeparationVelocityParameters(1, 25)
    modifiers += ModAlignmentVelocityParameters(0.5)
    super.defaultModifiers() ++ modifiers
  }
}
