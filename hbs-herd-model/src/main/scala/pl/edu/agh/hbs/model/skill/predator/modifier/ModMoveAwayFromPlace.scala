package pl.edu.agh.hbs.model.skill.predator.modifier

import pl.edu.agh.hbs.model.Vector
import pl.edu.agh.hbs.model.modifier_cardinality.Many
import pl.edu.agh.hbs.model.skill.Modifier

case class ModMoveAwayFromPlace(position: Vector) extends Modifier(Many)
