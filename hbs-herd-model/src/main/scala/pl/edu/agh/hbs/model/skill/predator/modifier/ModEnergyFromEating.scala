package pl.edu.agh.hbs.model.skill.predator.modifier

import pl.edu.agh.hbs.model.cardinality.One
import pl.edu.agh.hbs.model.skill.Modifier

case class ModEnergyFromEating(energy: Double) extends Modifier(One)
