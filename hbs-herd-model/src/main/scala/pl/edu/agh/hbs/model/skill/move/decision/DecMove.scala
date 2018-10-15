package pl.edu.agh.hbs.model.skill.move.decision

import pl.edu.agh.hbs.model.ModifierBuffer
import pl.edu.agh.hbs.model.skill.move.action.ActMove
import pl.edu.agh.hbs.model.skill.{Action, Decision}

object DecMove extends Decision {

  override val actions: List[Action] = List(ActMove)

  override def priority: Int = 3

  override def decision(modifiers: ModifierBuffer): Boolean = true

}
