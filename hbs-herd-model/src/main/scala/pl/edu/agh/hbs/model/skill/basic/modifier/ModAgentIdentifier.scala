package pl.edu.agh.hbs.model.skill.basic.modifier

import pl.edu.agh.hbs.model.modifier_cardinality.One
import pl.edu.agh.hbs.model.skill.Modifier

case class ModAgentIdentifier(id: String) extends Modifier(One)
