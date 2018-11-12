package pl.edu.agh.hbs.model

import pl.edu.agh.hbs.model.skill.Modifier

import scala.collection.immutable.List
import scala.collection.mutable.ListBuffer
import scala.reflect.ClassTag

class ModifierBuffer extends Serializable {

  private val modifiers: ListBuffer[Modifier] = scala.collection.mutable.ListBuffer.empty[Modifier]

  def update(elems: Seq[Modifier]): Unit = elems.foreach(e => update(e))

  def update(elem: Modifier): Unit = {
    val modOption = modifiers.collectFirst { case m if m.getClass == elem.getClass && m.label == elem.label => m }
    modOption match {
      case Some(m) => modifiers -= m
      case None =>
    }
    modifiers += elem
  }

  def -(elem: Modifier): Unit = modifiers -= elem

  def --(elems: Seq[Modifier]): Unit = modifiers --= elems

  def length: Int = modifiers.length

  def result: List[Modifier] = modifiers.result

  def iterator: Iterator[Modifier] = modifiers.iterator

  def getFirst[A <: Modifier : ClassTag]: A = getFirst[A]((_: A) => true)

  def getFirst[A <: Modifier : ClassTag](label: String): A = getFirst[A]((m: A) => m.label == label)

  def getFirst[A <: Modifier : ClassTag](predicate: A => Boolean): A = {
    val modOption = modifiers.collectFirst { case a: A if predicate(a) => a }
    modOption match {
      case Some(a) => a
      case None => throw new NoSuchElementException("No modifier of type " + typeName[A])
    }
  }

  def getAll[A <: Modifier : ClassTag]: Seq[A] = getAll[A]((_: A) => true)

  def getAll[A <: Modifier : ClassTag](labels: Seq[String]): Seq[A] = getAll[A]((m: A) => labels.contains(m.label))

  def getAll[A <: Modifier : ClassTag](predicate: A => Boolean): Seq[A] = {
    modifiers.collect { case a: A if predicate(a) => a }
  }

  private def typeName[T](implicit tag: ClassTag[T]) = tag.runtimeClass.toGenericString

}
