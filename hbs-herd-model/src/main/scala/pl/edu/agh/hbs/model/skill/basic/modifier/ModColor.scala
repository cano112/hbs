package pl.edu.agh.hbs.model.skill.basic.modifier

import pl.edu.agh.hbs.api.ui.dto.Color
import pl.edu.agh.hbs.model.skill.Modifier

case class ModColor(color: Color) extends Modifier {
  override def copy(): Modifier = ModColor(color)
}
