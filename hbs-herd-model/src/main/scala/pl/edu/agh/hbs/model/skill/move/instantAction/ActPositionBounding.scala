package pl.edu.agh.hbs.model.skill.move.instantAction

import pl.edu.agh.hbs.model
import pl.edu.agh.hbs.model.ModifierBuffer
import pl.edu.agh.hbs.model.propagation.CircularPropagation
import pl.edu.agh.hbs.model.skill.basic.message.MesVisibleAgent
import pl.edu.agh.hbs.model.skill.basic.modifier.{ModAgentIdentifier, ModPosition, ModPropagationRadius, ModVelocity}
import pl.edu.agh.hbs.model.skill.{Action, Message}

object ActPositionBounding extends Action {

  override def stepsDuration: Int = 1

  override def action(modifiers: ModifierBuffer): Seq[Message] = {
    val velocity = modifiers.getFirst[ModVelocity].velocity
    val oldPosition = modifiers.getFirst[ModPosition].position
    var position = oldPosition + (velocity / 10)
    position = model.Vector((position(0) + 2000 * 5) % 2000, (position(1) + 1500 * 5) % 1500)
    modifiers.update(ModPosition(position))

    val propagationRadius = modifiers.getFirst[ModPropagationRadius].radius
    val agentId = modifiers.getFirst[ModAgentIdentifier].id

    Seq(new MesVisibleAgent(new CircularPropagation(position, 1000), agentId, position, velocity))
  }
}
