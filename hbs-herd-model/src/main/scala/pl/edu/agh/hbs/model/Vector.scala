package pl.edu.agh.hbs.model

import scala.annotation.varargs

class Vector private(val value: Double*) extends Serializable {

  def +(other: Vector): Vector = new Vector(this.value.zipAll(other.value, 0.0, 0.0).map(e => e._1 + e._2): _*)

  def -(other: Vector): Vector = new Vector(this.value.zipAll(other.value, 0.0, 0.0).map(e => e._1 - e._2): _*)

  def /(scalar: Double): Vector = new Vector(this.value.map(e => e / scalar): _*)

  def *(scalar: Double): Vector = new Vector(this.value.map(e => e * scalar): _*)

  def unary_- : Vector = new Vector(this.value.map(e => -e): _*)

  def distance(other: Vector): Double = Math.sqrt(this.value.zipAll(other.value, 0.0, 0.0).map(e => Math.pow(e._1 - e._2, 2)).sum)

  def magnitude(): Double = Math.sqrt(this.value.map(e => Math.pow(e, 2)).sum)

  def unitVector(): Vector = this / magnitude()

  def apply(i: Int): Double = if (i < value.length) value(i) else 0

  def get(i: Int): Double = this (i)

  def toArray: Array[Double] = value.toArray

}

object Vector {
  @varargs def of(value: Double*): Vector = new Vector(value:_*)

  def apply(value: Double*): Vector = new Vector(value: _*)
}
