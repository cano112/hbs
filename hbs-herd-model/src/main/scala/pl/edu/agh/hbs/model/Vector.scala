package pl.edu.agh.hbs.model

class Vector private(val value: Double*) extends Serializable {

  def +(other: Vector): Vector = new Vector(this.value.zipAll(other.value, 0.0, 0.0).map(e => e._1 + e._2): _*)

  def -(other: Vector): Vector = new Vector(this.value.zipAll(other.value, 0.0, 0.0).map(e => e._1 - e._2): _*)

  def /(scalar: Double): Vector = new Vector(this.value.map(e => e / scalar): _*)

  def *(scalar: Double): Vector = new Vector(this.value.map(e => e * scalar): _*)

  def unary_- : Vector = new Vector(this.value.map(e => -e): _*)

  def distance(other: Vector): Double = Math.sqrt(this.value.zipAll(other.value, 0.0, 0.0).map(e => Math.pow(e._1 - e._2, 2)).sum)

  def magnitude(): Double = Math.sqrt(this.value.map(e => Math.pow(e, 2)).sum)

  def unitVector(): Vector = this / magnitude()

  def apply(i: Int): Double = value(i)

  def get(i: Int): Double = this (i)

}

object Vector {

  def apply(value: Double*): Vector = new Vector(value: _*)

}