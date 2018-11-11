package pl.edu.agh.hbs.model.skill.basic.modifier

import pl.edu.agh.hbs.model.Vector
import pl.edu.agh.hbs.model.skill.Modifier

case class ModPosition(position: Vector) extends Modifier {
  override def copy(): Modifier = ModPosition(position)
}
