package pl.edu.agh.hbs.model.skill.basic.action

import pl.edu.agh.hbs.model.ModifierBuffer
import pl.edu.agh.hbs.model.skill.{Action, Message}

object ActUnfulfilledConditionsMarker extends Action {

  override def stepsDuration: Int = 0

  override def action(modifiers: ModifierBuffer): Seq[Message] = {
    Seq()
  }

}
