package pl.edu.agh.hbs.model.skill.move.decision

import pl.edu.agh.hbs.model.skill.move.modifier.ModMoveDirection
import pl.edu.agh.hbs.model.skill.{Decision, Modifier}

import scala.collection.mutable.ListBuffer

object DecMove extends Decision {

  override def priority: Int = 3

  override def decision(modifiers: ListBuffer[Modifier]): Boolean = {
    if (true) {
      modifiers += new ModMoveDirection((r.nextInt(3) - 1) * 10, (r.nextInt(3) - 1) * 10)
      true
    }
    else
      false
  }

  private val r = scala.util.Random

}
