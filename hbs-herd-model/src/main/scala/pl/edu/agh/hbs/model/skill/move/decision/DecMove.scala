package pl.edu.agh.hbs.model.skill.move.decision

import pl.edu.agh.hbs.model.Vector
import pl.edu.agh.hbs.model.skill.basic.modifier.{ModSpeed, ModVisibleAgent}
import pl.edu.agh.hbs.model.skill.{Decision, Modifier}

import scala.collection.mutable.ListBuffer

object DecMove extends Decision {

  override def priority: Int = 3

  override def decision(modifiers: ListBuffer[Modifier]): Boolean = {
    val modVisibleAgents = Modifier.getAll[ModVisibleAgent](modifiers)
    if (modVisibleAgents.isEmpty) {
      modifiers += ModSpeed(new Vector((r.nextInt(3) - 1) * 10, (r.nextInt(3) - 1) * 10))
      true
    }
    else
      false
  }

  private val r = scala.util.Random

}
