package pl.edu.agh.hbs.model.skill.predator.modifier

import pl.edu.agh.hbs.model.SpeciesObject
import pl.edu.agh.hbs.model.skill.Modifier

case class ModHuntFor(prey: SpeciesObject) extends Modifier {
  override def copy(): Modifier = ModHuntFor(prey)
}
