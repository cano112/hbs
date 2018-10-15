package pl.edu.agh.hbs.model.skill.basic.modifier

import pl.edu.agh.hbs.model.Vector
import pl.edu.agh.hbs.model.cardinality.OneWithQualifier
import pl.edu.agh.hbs.model.skill.Modifier

case class ModVelocity(velocity: Vector, velocitySource: String) extends Modifier(OneWithQualifier, velocitySource)
