package pl.edu.agh.hbs.model.skill.predator.decision

import pl.edu.agh.hbs.model.cardinality.ModifierBuffer
import pl.edu.agh.hbs.model.skill.move.action.{ActMove, ActRecalculateVelocity2}
import pl.edu.agh.hbs.model.skill.{Action, Decision}

object DecHunt extends Decision {

  override val actions: List[Action] = List()

  override def priority: Int = 4

  override def decision(modifiers: ModifierBuffer): Boolean = true

}
