package pl.edu.agh.hbs.model.skill.basic.modifier

import pl.edu.agh.hbs.model.skill.Modifier

case class ModIdentifier(id: String) extends Modifier {
  override def copy(): Modifier = ModIdentifier(id)
}
