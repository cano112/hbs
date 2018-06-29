package pl.edu.agh.hbs.model.propagation

import pl.edu.agh.hbs.model.position.Position

abstract class Propagation {

  def isCovered(position: Position): Boolean

}
