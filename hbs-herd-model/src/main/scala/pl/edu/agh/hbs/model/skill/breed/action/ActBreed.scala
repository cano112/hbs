package pl.edu.agh.hbs.model.skill.breed.action

import pl.edu.agh.hbs.model.ModifierBuffer
import pl.edu.agh.hbs.model.propagation.CircularPropagation
import pl.edu.agh.hbs.model.skill.basic.message.MesVisibleAgent
import pl.edu.agh.hbs.model.skill.basic.modifier._
import pl.edu.agh.hbs.model.skill.predator.modifier.ModEnergy
import pl.edu.agh.hbs.model.skill.{Action, Message}

object ActBreed extends Action {

  override def stepsDuration: Int = 1

  override def action(modifiers: ModifierBuffer): Seq[Message] = {
    val oldPosition = modifiers.getFirst[ModPosition].position
    modifiers.update(ModEnergy(0))
    val agentId = modifiers.getFirst[ModAgentIdentifier].id

    Seq(new MesVisibleAgent(new CircularPropagation(position, 1000), agentId, position, velocity))
  }
}
