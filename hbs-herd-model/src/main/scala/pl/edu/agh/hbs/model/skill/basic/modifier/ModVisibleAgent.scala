package pl.edu.agh.hbs.model.skill.basic.modifier

import pl.edu.agh.hbs.model.Vector
import pl.edu.agh.hbs.model.modifier_cardinality.Many
import pl.edu.agh.hbs.model.skill.Modifier

case class ModVisibleAgent(agentId: String, position: Vector, speed: Vector) extends Modifier(Many)
