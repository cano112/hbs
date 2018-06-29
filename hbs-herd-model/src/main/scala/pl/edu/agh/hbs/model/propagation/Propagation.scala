package pl.edu.agh.hbs.model.propagation

import pl.edu.agh.hbs.model.skill.basic.modifier.position.Position

abstract class Propagation {

  def isCovered(position: Position): Boolean

}
