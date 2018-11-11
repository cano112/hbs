package pl.edu.agh.hbs.model.skill.dying.instantAction

import pl.edu.agh.hbs.model.skill.Action
import pl.edu.agh.hbs.model.skill.basic.modifier.ModLifeStatus
import pl.edu.agh.hbs.model.skill.dying.energy.ModEnergy
import pl.edu.agh.hbs.model.skill.predator.modifier.ModEnergyFromEating
import pl.edu.agh.hbs.model.{ModifierBuffer, StepOutput}

object ActConsumeEnergy extends Action {

  override def action(modifiers: ModifierBuffer): StepOutput = {
    val energy = modifiers.getFirst[ModEnergy].energy
    val energyFromEating = modifiers.getFirst[ModEnergyFromEating].energy
    modifiers.update(ModEnergy(energy - energyFromEating))
    if (energy - energyFromEating <= 0)
      modifiers.update(ModLifeStatus(false))

    new StepOutput()
  }
}
