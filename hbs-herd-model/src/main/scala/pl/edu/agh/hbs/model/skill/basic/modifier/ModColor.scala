package pl.edu.agh.hbs.model.skill.basic.modifier

import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.ui.dto.Color

case class ModColor(color: Color) extends Modifier {
  override def copy(): Modifier = ModColor(color)
}
