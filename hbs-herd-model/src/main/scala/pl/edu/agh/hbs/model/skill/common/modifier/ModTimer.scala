package pl.edu.agh.hbs.model.skill.common.modifier

import pl.edu.agh.hbs.model.skill.Modifier

case class ModTimer(timeSinceLastAction: Int, override val label: String) extends Modifier(label) {
  override def copy(): Modifier = ModTimer(timeSinceLastAction, label)
}
