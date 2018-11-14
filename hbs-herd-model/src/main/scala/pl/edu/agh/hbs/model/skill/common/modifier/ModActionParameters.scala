package pl.edu.agh.hbs.model.skill.common.modifier

import pl.edu.agh.hbs.model.skill.Modifier

case class ModActionParameters(override val label: String, parameters: Map[String, Double]) extends Modifier(label) {
  override def copy(): Modifier = ModActionParameters(label, parameters)
}
