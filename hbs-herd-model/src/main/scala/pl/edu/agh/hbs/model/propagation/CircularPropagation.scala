package pl.edu.agh.hbs.model.propagation

import pl.edu.agh.hbs.model.{Agent, Vector}

class CircularPropagation(val center: Vector, val radius: Double) extends Propagation {

  override def shouldReceive(receiver: Agent): Boolean = center.distance(receiver.position()) < radius

}
