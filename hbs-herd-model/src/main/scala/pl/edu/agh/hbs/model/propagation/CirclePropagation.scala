package pl.edu.agh.hbs.model.propagation

import pl.edu.agh.hbs.model.skill.basic.modifier.position.Position

class CirclePropagation extends Propagation {

  override def isCovered(position: Position): Boolean = true

}
