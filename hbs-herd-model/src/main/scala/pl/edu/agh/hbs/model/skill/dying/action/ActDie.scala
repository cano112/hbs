package pl.edu.agh.hbs.model.skill.dying.action

import pl.edu.agh.hbs.model.skill.Action
import pl.edu.agh.hbs.model.{ModifierBuffer, StepOutput}

object ActDie extends Action {

  override def stepsDuration: Int = 1

  override def action(modifiers: ModifierBuffer): StepOutput = {
    new StepOutput()
  }

}
