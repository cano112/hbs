package pl.edu.agh.hbs.model.skill.move.action

import pl.edu.agh.hbs.model.Vector
import pl.edu.agh.hbs.model.propagation.CirclePropagation
import pl.edu.agh.hbs.model.skill.basic.message.MesPosition
import pl.edu.agh.hbs.model.skill.basic.modifier._
import pl.edu.agh.hbs.model.skill.{Action, Message, Modifier}

import scala.collection.mutable.ListBuffer

object ActMove extends Action {

  override def stepsDuration: Int = 1

  override def action(modifiers: ListBuffer[Modifier]): Seq[Message] = {
    val modSpeed = Modifier.getFirst[ModSpeed](modifiers)
    val modPosition = Modifier.getFirst[ModPosition](modifiers)

    val propagationRadius = Modifier.getFirst[ModPositionPropagationRadius](modifiers).radius
    val representation = Modifier.getFirst[ModRepresentation](modifiers).representation
    val agentId = Modifier.getFirst[ModAgentIdentifier](modifiers).id
    val position = modPosition.position + modSpeed.speed

    modifiers -= modSpeed
    modifiers -= modPosition
    modifiers += ModPosition(position)

    Seq(new MesPosition(new CirclePropagation(position, propagationRadius), agentId, position, representation))
  }
}
