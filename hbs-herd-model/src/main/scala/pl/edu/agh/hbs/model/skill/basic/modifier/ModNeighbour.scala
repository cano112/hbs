package pl.edu.agh.hbs.model.skill.basic.modifier

import pl.edu.agh.hbs.model.Vector
import pl.edu.agh.hbs.model.cardinality.OneWithQualifier
import pl.edu.agh.hbs.model.skill.Modifier

case class ModNeighbour(agentId: String, position: Vector, velocity: Vector) extends Modifier(OneWithQualifier, agentId)
