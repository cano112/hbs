package pl.edu.agh.hbs.model.skill.breeding.decision

import pl.edu.agh.hbs.model.ModifierBuffer
import pl.edu.agh.hbs.model.skill.breeding.action.ActBreed
import pl.edu.agh.hbs.model.skill.breeding.modifier.ModBreedParameters
import pl.edu.agh.hbs.model.skill.common.modifier.ModTimer
import pl.edu.agh.hbs.model.skill.{Action, Decision}

object DecBreed extends Decision {

  override val actions: List[Action] = List(ActBreed)

  override def priority: Int = 4

  override def decision(modifiers: ModifierBuffer): Boolean = {
    val breedThreshold = modifiers.getFirst[ModBreedParameters].breedTimer
    val timer = modifiers.getFirst[ModTimer]("breed").timeSinceLastAction
    timer > breedThreshold
  }

}
