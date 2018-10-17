package pl.edu.agh.hbs.model.skill.breed.instantAction

import pl.edu.agh.hbs.model.ModifierBuffer
import pl.edu.agh.hbs.model.skill.breed.modifier.ModBreedTime
import pl.edu.agh.hbs.model.skill.predator.modifier.ModEnergy
import pl.edu.agh.hbs.model.skill.{Action, Message}

object ActIncrementBreedTime extends Action {

  override def action(modifiers: ModifierBuffer): Seq[Message] = {
    val time = modifiers.getFirst[ModBreedTime].lastBreedTime
    modifiers.update(ModEnergy(time + 1))

    Seq()
  }
}
