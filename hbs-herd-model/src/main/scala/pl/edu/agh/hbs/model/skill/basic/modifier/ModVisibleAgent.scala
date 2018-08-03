package pl.edu.agh.hbs.model.skill.basic.modifier

import pl.edu.agh.hbs.core.providers.Representation
import pl.edu.agh.hbs.model.Position
import pl.edu.agh.hbs.model.skill.Modifier

case class ModVisibleAgent(agentId: String, position: Position, representation: Representation) extends Modifier
