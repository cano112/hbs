package pl.edu.agh.hbs.model.skill.dying.instantAction

import pl.edu.agh.hbs.model.skill.Action
import pl.edu.agh.hbs.model.skill.basic.modifier.ModLifeStatus
import pl.edu.agh.hbs.model.skill.dying.energy.ModEnergy
import pl.edu.agh.hbs.model.{ModifierBuffer, StepOutput}

object ActConsumeEnergy extends Action {

  override def action(modifiers: ModifierBuffer): StepOutput = {
    val energy = modifiers.getFirst[ModEnergy]("standard").energy
    val consumedEnergy = modifiers.getFirst[ModEnergy]("consumed").energy
    modifiers.update(ModEnergy(energy - consumedEnergy, "standard"))
    if (energy - consumedEnergy <= 0)
      modifiers.update(ModLifeStatus(false))
    new StepOutput()
  }
}
