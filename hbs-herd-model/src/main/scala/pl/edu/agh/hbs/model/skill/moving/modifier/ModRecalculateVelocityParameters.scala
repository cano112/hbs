package pl.edu.agh.hbs.model.skill.moving.modifier

import pl.edu.agh.hbs.model.skill.Modifier

case class ModRecalculateVelocityParameters(maxVelocity: Double) extends Modifier {
  override def copy(): Modifier = ModRecalculateVelocityParameters(maxVelocity)
}
