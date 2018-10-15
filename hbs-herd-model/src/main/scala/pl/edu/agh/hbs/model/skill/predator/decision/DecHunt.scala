package pl.edu.agh.hbs.model.skill.predator.decision

import pl.edu.agh.hbs.model.ModifierBuffer
import pl.edu.agh.hbs.model.skill.{Action, Decision}

object DecHunt extends Decision {

  override val actions: List[Action] = List()

  override def priority: Int = 4

  override def decision(modifiers: ModifierBuffer): Boolean = true

}
