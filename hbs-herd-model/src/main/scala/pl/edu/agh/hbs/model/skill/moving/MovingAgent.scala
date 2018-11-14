package pl.edu.agh.hbs.model.skill.moving

import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.skill.moving.decision.DecMove
import pl.edu.agh.hbs.model.skill.moving.instantAction.ActRecalculateVelocity
import pl.edu.agh.hbs.model.skill.moving.modifier.{ModMoveParameters, ModRecalculateVelocityParameters}
import pl.edu.agh.hbs.model.{Agent, ModifierBuffer}

import scala.collection.mutable.ListBuffer

trait MovingAgent extends Agent {
  this.beforeStepActions += ActRecalculateVelocity
  this.decisions += DecMove
  this.modifiers.update(ModMoveParameters(0.1, 1000))
  this.modifiers.update(ModRecalculateVelocityParameters(50))

  override def parametersCopiedForChild(modifiers: ModifierBuffer): Seq[Modifier] = {
    val initModifiers = ListBuffer.empty[Modifier]
    initModifiers += modifiers.getFirst[ModMoveParameters].copy()
    initModifiers += modifiers.getFirst[ModRecalculateVelocityParameters].copy()
    initModifiers ++ super.parametersCopiedForChild(modifiers)
  }
}
