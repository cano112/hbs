package pl.edu.agh.hbs.model.skill.common.instantAction

import pl.edu.agh.hbs.model.skill.Action
import pl.edu.agh.hbs.model.skill.common.modifier.ModTimer
import pl.edu.agh.hbs.model.{ModifierBuffer, StepOutput}

object ActIncrementTimers extends Action {

  override def action(modifiers: ModifierBuffer): StepOutput = {
    val timers = modifiers.getAll[ModTimer]
    timers.foreach(t => modifiers.update(ModTimer(t.timeSinceLastAction + 1, t.label)))
    new StepOutput()
  }
}
