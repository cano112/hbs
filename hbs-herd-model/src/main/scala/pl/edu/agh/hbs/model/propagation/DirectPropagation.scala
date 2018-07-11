package pl.edu.agh.hbs.model.propagation

import pl.edu.agh.hbs.model.Agent

class DirectPropagation(val receiver: Agent) extends Propagation {

  override def shouldReceive(receiver: Agent): Boolean = receiver == this.receiver

}
