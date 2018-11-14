package pl.edu.agh.hbs.model.perception

import pl.edu.agh.hbs.model.{Agent, Vector}

class CircularSectorPerception(val radius: Double, val angleOfView: Int, val pointToObserve: Vector) extends Perception {

  override def shouldProcess(receiver: Agent): Boolean = receiver.position().distance(pointToObserve) < radius

}
