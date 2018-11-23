package pl.edu.agh.hbs.model.skill.predator.modifier

import pl.edu.agh.hbs.model.skill.Modifier

case class ModHuntParameters(preyDistance: Double) extends Modifier {
  override def copy(): Modifier = ModHuntParameters(preyDistance)
}
