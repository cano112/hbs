package pl.edu.agh.hbs.model.skill.basic.modifier

import pl.edu.agh.hbs.model.skill.Modifier

case class ModLifeStatus(alive: Boolean = true) extends Modifier {
  override def copy(): Modifier = ModLifeStatus(alive)
}
