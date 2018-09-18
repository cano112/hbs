package pl.edu.agh.hbs.model.modifier_cardinality

import pl.edu.agh.hbs.model.skill.Modifier

import scala.collection.mutable.ListBuffer

object Many extends Cardinality {
  override def addTo(modifier: Modifier, modifiers: ListBuffer[Modifier]): Unit = modifiers += modifier
}