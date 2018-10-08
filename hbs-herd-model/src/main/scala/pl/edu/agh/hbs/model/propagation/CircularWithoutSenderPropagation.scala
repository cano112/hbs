package pl.edu.agh.hbs.model.propagation

import pl.edu.agh.hbs.model.{Agent, Vector}

class CircularWithoutSenderPropagation(val center: Vector, val radius: Double, val senderId: String) extends Propagation {

  override def shouldReceive(receiver: Agent): Boolean = center.distance(receiver.position()) < radius && receiver.id() != senderId

}
