package pl.edu.agh.hbs.model.skill.dying.decision

import pl.edu.agh.hbs.model.ModifierBuffer
import pl.edu.agh.hbs.model.skill.common.modifier.ModEnergy
import pl.edu.agh.hbs.model.skill.dying.action.ActDie
import pl.edu.agh.hbs.model.skill.{Action, Decision}

object DecDie extends Decision {

  override val actions: List[Action] = List(ActDie)

  override def priority: Int = 100

  override def decision(modifiers: ModifierBuffer): Boolean = modifiers.getFirst[ModEnergy]("standard").energy < 0

}
