package pl.edu.agh.hbs.model.skill.basic.modifier.position


class Cartesian2DPosition private(val x: Int, val y: Int) extends Position {

  def +(other: Cartesian2DPosition) = Cartesian2DPosition(x + other.x, y + other.y)

  def -(other: Cartesian2DPosition) = Cartesian2DPosition(x - other.x, y - other.y)

  def unary_- = Cartesian2DPosition(-x, -y)

  def distance(other: Cartesian2DPosition): Double =
    Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2))

  override def toString: String = "(" + x.toString + "," + y.toString + ")"

  override def hashCode: Int = 41 * (41 + x) + y

  override def equals(other: Any): Boolean = other match {
    case that: Cartesian2DPosition =>
      (that canEqual this) && (this.x == that.x) && (this.y == that.y)
    case _ =>
      false
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Cartesian2DPosition]
}

object Cartesian2DPosition {
  def apply(x: Int, y: Int) = new Cartesian2DPosition(x, y)

  def apply() = new Cartesian2DPosition(0, 0)

  def apply(prototype: Cartesian2DPosition) = new Cartesian2DPosition(prototype.x, prototype.y)
}