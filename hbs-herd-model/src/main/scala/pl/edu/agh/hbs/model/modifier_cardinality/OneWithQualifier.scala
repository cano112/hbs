package pl.edu.agh.hbs.model.modifier_cardinality

import pl.edu.agh.hbs.model.skill.Modifier

import scala.collection.mutable.ListBuffer

object OneWithQualifier extends Cardinality {
  override def addTo(modifier: Modifier, modifiers: ListBuffer[Modifier]): Unit = {
    val modOption = modifiers.collectFirst { case a if a.getClass == modifier.getClass && a.qualifier == modifier.qualifier => a }
    modOption match {
      case Some(m) => modifiers -= m
      case None =>
    }
    modifiers += modifier  }
}
