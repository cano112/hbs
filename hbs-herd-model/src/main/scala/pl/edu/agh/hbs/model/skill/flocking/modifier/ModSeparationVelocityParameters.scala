package pl.edu.agh.hbs.model.skill.flocking.modifier

import pl.edu.agh.hbs.model.skill.Modifier

case class ModSeparationVelocityParameters(separationFactor: Double, minimalDistance: Double) extends Modifier {
  override def copy(): Modifier = ModSeparationVelocityParameters(separationFactor, minimalDistance)
}
