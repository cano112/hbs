package pl.edu.agh.hbs.model.perception

import pl.edu.agh.hbs.model.Agent

abstract class Perception extends Serializable {

  def shouldProcess(receiver: Agent): Boolean

}
