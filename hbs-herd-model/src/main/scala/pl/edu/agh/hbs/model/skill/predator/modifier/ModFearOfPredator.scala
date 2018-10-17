package pl.edu.agh.hbs.model.skill.predator.modifier

import pl.edu.agh.hbs.model.cardinality.One
import pl.edu.agh.hbs.model.skill.Modifier

case class ModFearOfPredator(lastBreedTime: Int) extends Modifier(One)
