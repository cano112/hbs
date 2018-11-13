package pl.edu.agh.hbs.model

import scala.annotation.varargs

class Vector(val value: Int*) extends Serializable {

  def +(other: Vector): Vector = new Vector(this.value.zipAll(other.value, 0, 0).map(e => e._1 + e._2): _*)

  def -(other: Vector): Vector = new Vector(this.value.zipAll(other.value, 0, 0).map(e => e._1 - e._2): _*)

  def unary_- : Vector = new Vector(this.value.map(e => -e): _*)

  def distance(other: Vector): Double = Math.sqrt(this.value.zipAll(other.value, 0, 0).map(e => Math.pow(e._1 - e._2, 2)).sum)

  def apply(i: Int): Int = value(i)

  def get(i: Int): Int = this (i)

  def toArray: Array[Int] = value.toArray
}

object Vector {
  @varargs def of(value: Int*): Vector = new Vector(value:_*)
}
