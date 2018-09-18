package pl.edu.agh.hbs.model.skill
import pl.edu.agh.hbs.model.modifier_cardinality.Cardinality

import scala.reflect.ClassTag

abstract class Modifier(cardinality: Cardinality) extends Serializable

object Modifier {
  def getFirst[A <: Modifier : ClassTag](modifiers: Seq[_ <: Modifier]): A = getFirst[A]((_: A) => true)(modifiers)

  def getFirst[A <: Modifier : ClassTag](predicate: A => Boolean)(modifiers: Seq[_ <: Modifier]): A = {
    val modOption = modifiers.collectFirst { case a: A if predicate(a) => a }
    modOption match {
      case Some(a) => a
      case None => throw new NoSuchElementException("No modifier of type " + typeName[A])
    }
  }

  def getAll[A <: Modifier : ClassTag](modifiers: Seq[_ <: Modifier]): Seq[A] = getAll[A]((_: A) => true)(modifiers)

  def getAll[A <: Modifier : ClassTag](predicate: A => Boolean)(modifiers: Seq[_ <: Modifier]): Seq[A] = {
    modifiers.collect { case a: A if predicate(a) => a }
  }

  private def typeName[T](implicit tag: ClassTag[T]) = tag.runtimeClass.toGenericString
}
