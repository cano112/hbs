package pl.edu.agh.hbs.model.skill.common.modifier

import pl.edu.agh.hbs.model.Vector
import pl.edu.agh.hbs.model.skill.Modifier

case class ModVelocity(velocity: Vector, override val label: String) extends Modifier(label) {
  override def copy(): Modifier = ModVelocity(velocity, label)
}
