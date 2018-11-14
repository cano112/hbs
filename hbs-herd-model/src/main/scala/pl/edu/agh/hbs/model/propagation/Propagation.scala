package pl.edu.agh.hbs.model.propagation

import pl.edu.agh.hbs.model
import pl.edu.agh.hbs.model.Agent

abstract class Propagation extends Serializable {

  def shouldReceive(receiver: Agent): Boolean

  def shouldSend(leftBottom: model.Vector, rightUpper: model.Vector): Boolean

}
