package pl.edu.agh.hbs.model.skill.dying.action

import pl.edu.agh.hbs.model.propagation.CircularPropagation
import pl.edu.agh.hbs.model.skill.Action
import pl.edu.agh.hbs.model.skill.basic.modifier.{ModIdentifier, ModPosition}
import pl.edu.agh.hbs.model.skill.dying.message.MesIsDead
import pl.edu.agh.hbs.model.{ModifierBuffer, StepOutput}

import scala.collection.mutable.ListBuffer

object ActIsDead extends Action {

  override def stepsDuration: Int = Int.MaxValue

  override def action(modifiers: ModifierBuffer): StepOutput = {
    val position = modifiers.getFirst[ModPosition].position
    val id = modifiers.getFirst[ModIdentifier].id
    new StepOutput(
      messages = ListBuffer(new MesIsDead(new CircularPropagation(position, 1000), id)),
      isDead = true)
  }

}
