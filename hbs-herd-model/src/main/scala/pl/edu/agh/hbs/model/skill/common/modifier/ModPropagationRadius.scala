package pl.edu.agh.hbs.model.skill.common.modifier

import pl.edu.agh.hbs.model.skill.Modifier

case class ModPropagationRadius(radius: Double, override val label: String) extends Modifier(label) {
  override def copy(): Modifier = ModPropagationRadius(radius, label)
}
