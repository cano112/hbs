package pl.edu.agh.hbs.model.skill.basic.action

import pl.edu.agh.hbs.model.skill.Action
import pl.edu.agh.hbs.model.{ModifierBuffer, StepOutput}

object ActDecideAgain extends Action {

  override def stepsDuration: Int = 0

  override def action(modifiers: ModifierBuffer): StepOutput = {
    new StepOutput()
  }

}
