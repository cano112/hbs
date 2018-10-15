package pl.edu.agh.hbs.model.skill.predator.instantAction

import pl.edu.agh.hbs.model.ModifierBuffer
import pl.edu.agh.hbs.model.skill.basic.modifier.ModLifeStatus
import pl.edu.agh.hbs.model.skill.predator.modifier.{ModEnergy, ModEnergyFromEating}
import pl.edu.agh.hbs.model.skill.{Action, Message}

object ActConsumeEnergy extends Action {

  override def action(modifiers: ModifierBuffer): Seq[Message] = {
    val energy = modifiers.getFirst[ModEnergy].energy
    val energyFromEating = modifiers.getFirst[ModEnergyFromEating].energy
    modifiers.update(ModEnergy(energy - energyFromEating))
    if (energy - energyFromEating <= 0)
      modifiers.update(ModLifeStatus(false))

    Seq()
  }
}
