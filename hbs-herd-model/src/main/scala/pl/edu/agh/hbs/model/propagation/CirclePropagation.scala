package pl.edu.agh.hbs.model.propagation

import pl.edu.agh.hbs.model.{Agent, Position}

class CirclePropagation(val center: Position, val radius: Double) extends Propagation {

  override def shouldReceive(receiver: Agent): Boolean = center.distance(receiver.position) < radius

}
