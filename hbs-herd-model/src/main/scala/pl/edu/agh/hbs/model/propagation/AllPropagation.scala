package pl.edu.agh.hbs.model.propagation

import pl.edu.agh.hbs.model.{Agent, Vector}

class AllPropagation() extends Propagation {

  override def shouldReceive(receiver: Agent): Boolean = true

  def shouldSend(leftBottom: Vector, rightUpper: Vector): Boolean = true

}
