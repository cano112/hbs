package pl.edu.agh.hbs.model.skill.breeding.decision

import pl.edu.agh.hbs.model.ModifierBuffer
import pl.edu.agh.hbs.model.skill.breeding.action.ActBreed
import pl.edu.agh.hbs.model.skill.common.modifier.ModTimer
import pl.edu.agh.hbs.model.skill.{Action, Decision}

object DecBreed extends Decision {

  override val actions: List[Action] = List(ActBreed)

  override def priority: Int = 4

  override def decision(modifiers: ModifierBuffer): Boolean =
    modifiers.getFirst[ModTimer]("breed").timeSinceLastAction > 4

}
