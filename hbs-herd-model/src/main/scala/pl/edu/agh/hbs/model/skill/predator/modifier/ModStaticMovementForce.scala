package pl.edu.agh.hbs.model.skill.predator.modifier

import pl.edu.agh.hbs.model.Vector
import pl.edu.agh.hbs.model.cardinality.Many
import pl.edu.agh.hbs.model.skill.Modifier

case class ModStaticMovementForce(position: Vector) extends Modifier(Many)
