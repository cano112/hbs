package pl.edu.agh.hbs.model.cardinality

import pl.edu.agh.hbs.model.skill.Modifier

import scala.collection.mutable.ListBuffer

abstract class Cardinality extends Serializable {
  def addTo(modifier: Modifier, modifiers: ListBuffer[Modifier])
}
