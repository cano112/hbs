package pl.edu.agh.hbs.model.skill.basic.action

import pl.edu.agh.hbs.model.{ModifierBuffer, StepOutput}
import pl.edu.agh.hbs.model.skill.{Action, Message}

object ActWaitStep extends Action {

  override def stepsDuration: Int = 1

  override def action(modifiers: ModifierBuffer): StepOutput = {
    new StepOutput()
  }

}