package pl.edu.agh.hbs.model.position

abstract class Position(val value: Int*) {

  def +(other: Position): Position

  def -(other: Position): Position

  def unary_- : Position

  def distance(other: Position): Double

  override def toString: String

  override def hashCode: Int

  override def equals(other: Any): Boolean

  def canEqual(other: Any): Boolean

}
