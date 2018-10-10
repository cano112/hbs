package pl.edu.agh.hbs.model.skill.basic.modifier

import pl.edu.agh.hbs.model.Vector
import pl.edu.agh.hbs.model.cardinality.One
import pl.edu.agh.hbs.model.skill.Modifier

case class ModVelocity(velocity: Vector) extends Modifier(One)
