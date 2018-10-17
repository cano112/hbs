package pl.edu.agh.hbs.model.skill.breed.modifier

import pl.edu.agh.hbs.model.cardinality.One
import pl.edu.agh.hbs.model.skill.Modifier

case class ModBreedTime(lastBreedTime: Int) extends Modifier(One)
