package pl.edu.agh.hbs.model.skill.moving.modifier

import pl.edu.agh.hbs.model.skill.Modifier

case class ModMoveParameters(velocityFactor: Double, propagationRadius: Double, maxVelocity: Double) extends Modifier {
  override def copy(): Modifier = ModMoveParameters(velocityFactor, propagationRadius, maxVelocity)
}
