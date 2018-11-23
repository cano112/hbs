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
  this.modifiers.update(ModCohesionVelocityParameters(1 / 7.5))
  this.modifiers.update(ModSeparationVelocityParameters(1, 25))
  this.modifiers.update(ModAlignmentVelocityParameters(0.5))

  override def modifiersCopiedForChild(modifiers: ModifierBuffer): Seq[Modifier] = {
    val initModifiers = ListBuffer.empty[Modifier]
    modifiers.getAll[ModCohesionVelocityParameters].foreach(m => initModifiers += m.copy())
    modifiers.getAll[ModSeparationVelocityParameters].foreach(m => initModifiers += m.copy())
    modifiers.getAll[ModAlignmentVelocityParameters].foreach(m => initModifiers += m.copy())
    initModifiers ++ super.modifiersCopiedForChild(modifiers)
  }
}
