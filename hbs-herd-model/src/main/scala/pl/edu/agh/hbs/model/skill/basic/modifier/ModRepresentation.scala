package pl.edu.agh.hbs.model.skill.basic.modifier

import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.ui.Representation

case class ModRepresentation(representation: Representation) extends Modifier {
  override def copy(): Modifier = ModRepresentation(representation)
}
