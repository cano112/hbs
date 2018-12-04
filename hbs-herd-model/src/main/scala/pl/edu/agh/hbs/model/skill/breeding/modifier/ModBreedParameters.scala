package pl.edu.agh.hbs.model.skill.breeding.modifier

import pl.edu.agh.hbs.model.skill.Modifier

case class ModBreedParameters(breedTimer: Int, propagationRadius: Double) extends Modifier {
  override def copy(): Modifier = ModBreedParameters(breedTimer, propagationRadius)
}
