package pl.edu.agh.hbs.model.skill.predator.modifier

import pl.edu.agh.hbs.model.SpeciesObject
import pl.edu.agh.hbs.model.skill.Modifier

case class ModFearOf(predator: SpeciesObject) extends Modifier {
  override def copy(): Modifier = ModFearOf(predator)
}
