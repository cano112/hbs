package pl.edu.agh.hbs.model.skill

import pl.edu.agh.hbs.model.modifier_cardinality.Cardinality

abstract class Modifier(final val cardinality: Cardinality, val qualifier: String = "") extends Serializable
