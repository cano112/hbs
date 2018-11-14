package pl.edu.agh.hbs.model.perception

import pl.edu.agh.hbs.model.Agent

class ExcludePerception(val id: String) extends Perception {

  override def shouldProcess(receiver: Agent): Boolean = receiver.id() != id

}
