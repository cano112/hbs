package pl.edu.agh.hbs.model.skill.move.action

import pl.edu.agh.hbs.model.modifier_cardinality.ModifierBuffer
import pl.edu.agh.hbs.model.propagation.CirclePropagation
import pl.edu.agh.hbs.model.skill.basic.message.MesVisibleAgent
import pl.edu.agh.hbs.model.skill.basic.modifier._
import pl.edu.agh.hbs.model.skill.{Action, Message}

object ActMove extends Action {

  override def stepsDuration: Int = 1

  override def action(modifiers: ModifierBuffer): Seq[Message] = {
    val speed = modifiers.getFirst[ModSpeed].speed
    val oldPosition = modifiers.getFirst[ModPosition].position
    val propagationRadius = modifiers.getFirst[ModPositionPropagationRadius].radius
    val agentId = modifiers.getFirst[ModAgentIdentifier].id
    val position = oldPosition + speed

    modifiers.update(ModPosition(position))
    Seq(new MesVisibleAgent(new CirclePropagation(position, propagationRadius), agentId, position, speed))
  }
}
