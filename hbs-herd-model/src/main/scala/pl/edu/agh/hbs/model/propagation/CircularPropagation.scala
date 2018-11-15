package pl.edu.agh.hbs.model.propagation


import pl.edu.agh.hbs.model.{Agent, Vector}

class CircularPropagation(val center: Vector, val radius: Double) extends Propagation {

  override def shouldReceive(receiver: Agent): Boolean = center.distance(receiver.position()) < radius

  def shouldSend(leftBottom: Vector, rightUpper: Vector): Boolean = {
    val rightBottom = Vector(rightUpper(0), leftBottom(1))
    val leftUpper = Vector(leftBottom(0), rightUpper(1))
    crossLineAndCircle(leftBottom, leftUpper) ||
      crossLineAndCircle(rightBottom, rightUpper) ||
      crossLineAndCircle(rightBottom, leftBottom) ||
      crossLineAndCircle(leftUpper, rightUpper)
  }

  private def crossLineAndCircle(p1: Vector, p2: Vector): Boolean = {
    val x1 = p1(0)
    val y1 = p1(1)
    val x2 = p2(0)
    val y2 = p2(1)
    val a = y1 - y2
    val b = x2 - x1
    val c = (x1 - x2) * y1 + (y2 - y1) * x1

    val x = center(0)
    val y = center(1)
    val dist = Math.abs(a * x + b * y + c) / Math.sqrt(a * a + b * b)

    radius >= dist
  }

}
