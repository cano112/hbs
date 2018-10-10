package pl.edu.agh.hbs.model.skill.move.decision

import pl.edu.agh.hbs.model.cardinality.ModifierBuffer
import pl.edu.agh.hbs.model.skill.move.action.{ActMove, ActRecalculateVelocity, ActRecalculateVelocity2}
import pl.edu.agh.hbs.model.skill.{Action, Decision}

object DecMove extends Decision {

  override val actions: List[Action] = List(ActRecalculateVelocity2, ActMove)

  override def priority: Int = 3

  override def decision(modifiers: ModifierBuffer): Boolean = true

}
