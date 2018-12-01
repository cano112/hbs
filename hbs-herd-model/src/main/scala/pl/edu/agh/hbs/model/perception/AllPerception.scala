package pl.edu.agh.hbs.model.perception

import pl.edu.agh.hbs.model.Agent

class AllPerception extends Perception {

  override def shouldProcess(receiver: Agent): Boolean = true

}
