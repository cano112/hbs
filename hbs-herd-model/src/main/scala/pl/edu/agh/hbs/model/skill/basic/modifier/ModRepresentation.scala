package pl.edu.agh.hbs.model.skill.basic.modifier

import pl.edu.agh.hbs.ui.Representation
import pl.edu.agh.hbs.ui.dto.Colour
import pl.edu.agh.hbs.model.skill.Modifier

case class ModRepresentation(representation: Representation, colour: Colour) extends Modifier {
  override def copy(): Modifier = ModRepresentation(representation, colour)
}
