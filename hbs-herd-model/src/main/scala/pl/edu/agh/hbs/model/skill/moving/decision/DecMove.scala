package pl.edu.agh.hbs.model.skill.moving.decision

import pl.edu.agh.hbs.model.ModifierBuffer
import pl.edu.agh.hbs.model.skill.common.modifier.ModVelocity
import pl.edu.agh.hbs.model.skill.moving.action.ActMove
import pl.edu.agh.hbs.model.skill.moving.instantAction.ActRecalculateVelocity
import pl.edu.agh.hbs.model.skill.{Action, Decision}

object DecMove extends Decision {

  override val actions: List[Action] = List(ActRecalculateVelocity, ActMove)

  override def priority: Int = 3

  override def decision(modifiers: ModifierBuffer): Boolean = {
    val velocities = modifiers.getAll[ModVelocity]
    velocities.nonEmpty
  }

}
