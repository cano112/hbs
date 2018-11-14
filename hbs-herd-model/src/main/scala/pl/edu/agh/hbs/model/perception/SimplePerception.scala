package pl.edu.agh.hbs.model.perception

import pl.edu.agh.hbs.model.Agent

class SimplePerception extends Perception {

  override def shouldProcess(receiver: Agent): Boolean = true

}
