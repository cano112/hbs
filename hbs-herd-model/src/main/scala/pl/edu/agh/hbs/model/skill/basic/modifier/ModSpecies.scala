package pl.edu.agh.hbs.model.skill.basic.modifier

import pl.edu.agh.hbs.model.SpeciesObject
import pl.edu.agh.hbs.model.skill.Modifier

case class ModSpecies(species: SpeciesObject) extends Modifier {
  override def copy(): Modifier = ModSpecies(species)
}
