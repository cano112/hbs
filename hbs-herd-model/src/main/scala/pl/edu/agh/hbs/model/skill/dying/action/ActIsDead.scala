package pl.edu.agh.hbs.model.skill.dying.action

import pl.edu.agh.hbs.model.skill.Action
import pl.edu.agh.hbs.model.skill.basic.modifier.ModIdentifier
import pl.edu.agh.hbs.model.skill.dying.message.MesIsDead
import pl.edu.agh.hbs.model.{ModifierBuffer, StepOutput}

import scala.collection.mutable.ListBuffer

object ActIsDead extends Action {

  override def stepsDuration: Int = Int.MaxValue

  override def action(modifiers: ModifierBuffer): StepOutput = {
    val id = modifiers.getFirst[ModIdentifier].id
    new StepOutput(messages = ListBuffer(new MesIsDead(id)), isDead = true)
  }

}
