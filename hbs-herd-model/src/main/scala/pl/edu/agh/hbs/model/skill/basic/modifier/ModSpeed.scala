package pl.edu.agh.hbs.model.skill.basic.modifier

import pl.edu.agh.hbs.model.Vector
import pl.edu.agh.hbs.model.modifier_cardinality.One
import pl.edu.agh.hbs.model.skill.Modifier

case class ModSpeed(speed: Vector) extends Modifier(One)
