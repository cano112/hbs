package pl.edu.agh.hbs.model.skill.predator.decision

import pl.edu.agh.hbs.model.ModifierBuffer
import pl.edu.agh.hbs.model.skill.predator.modifier.ModEnergy
import pl.edu.agh.hbs.model.skill.{Action, Decision}

object DecDie extends Decision {

  override val actions: List[Action] = List()

  override def priority: Int = 100

  override def decision(modifiers: ModifierBuffer): Boolean = modifiers.getFirst[ModEnergy].energy < 0

}
