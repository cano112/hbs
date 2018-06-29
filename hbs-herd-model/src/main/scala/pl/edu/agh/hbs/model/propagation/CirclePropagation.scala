package pl.edu.agh.hbs.model.propagation

import pl.edu.agh.hbs.model.position.Position

class CirclePropagation extends Propagation {

  override def isCovered(position: Position): Boolean = true

}
