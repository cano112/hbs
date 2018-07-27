package pl.edu.agh.hbs.model.propagation

import pl.edu.agh.hbs.model.Agent

abstract class Propagation {

  def shouldReceive(receiver: Agent): Boolean

}
