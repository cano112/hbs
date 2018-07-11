package pl.edu.agh.hbs.model

class Position(val value: Int*) {

  def +(other: Position): Position = new Position(this.value.zipAll(other.value, 0, 0).map(e => e._1 + e._2): _*)

  def -(other: Position): Position = new Position(this.value.zipAll(other.value, 0, 0).map(e => e._1 - e._2): _*)

  def unary_- : Position = new Position(this.value.map(e => -e): _*)

  def distance(other: Position): Double = Math.sqrt(this.value.zipAll(other.value, 0, 0).map(e => Math.pow(e._1 - e._2, 2)).sum)

}
