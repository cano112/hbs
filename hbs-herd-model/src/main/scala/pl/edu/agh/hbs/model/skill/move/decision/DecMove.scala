package pl.edu.agh.hbs.model.skill.move.decision

import pl.edu.agh.hbs.model.Vector
import pl.edu.agh.hbs.model.modifier_cardinality.ModifierBuffer
import pl.edu.agh.hbs.model.skill.Decision
import pl.edu.agh.hbs.model.skill.basic.modifier.{ModSpeed, ModVisibleAgent}

object DecMove extends Decision {

  override def priority: Int = 3

  override def decision(modifiers: ModifierBuffer): Boolean = {
    val modVisibleAgents = modifiers.getAll[ModVisibleAgent]
    if (modVisibleAgents.isEmpty) {
      modifiers.update(ModSpeed(new Vector((r.nextInt(3) - 1) * 10, (r.nextInt(3) - 1) * 10)))
      true
    }
    else
      false
  }

  private val r = scala.util.Random

}
