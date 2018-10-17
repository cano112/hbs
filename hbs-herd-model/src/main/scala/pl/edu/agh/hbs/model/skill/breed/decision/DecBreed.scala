package pl.edu.agh.hbs.model.skill.breed.decision

import pl.edu.agh.hbs.model.ModifierBuffer
import pl.edu.agh.hbs.model.skill.breed.modifier.ModBreedTime
import pl.edu.agh.hbs.model.skill.{Action, Decision}

object DecBreed extends Decision {

  override val actions: List[Action] = List()

  override def priority: Int = 4

  override def decision(modifiers: ModifierBuffer): Boolean = modifiers.getFirst[ModBreedTime].lastBreedTime > 4

}
