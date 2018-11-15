package pl.edu.agh.hbs.model.propagation

import pl.edu.agh.hbs.model.{Agent, Vector}

abstract class Propagation extends Serializable {

  def shouldReceive(receiver: Agent): Boolean

  def shouldSend(leftBottom: Vector, rightUpper: Vector): Boolean

}
