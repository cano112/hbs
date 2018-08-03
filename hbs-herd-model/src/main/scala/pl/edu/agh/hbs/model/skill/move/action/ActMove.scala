package pl.edu.agh.hbs.model.skill.move.action

import pl.edu.agh.hbs.model.Position
import pl.edu.agh.hbs.model.propagation.CirclePropagation
import pl.edu.agh.hbs.model.skill.basic.message.MesPosition
import pl.edu.agh.hbs.model.skill.basic.modifier.{ModAgentIdentifier, ModPosition, ModPositionPropagationRadius, ModRepresentation}
import pl.edu.agh.hbs.model.skill.move.modifier.ModMoveDirection
import pl.edu.agh.hbs.model.skill.{Action, Message, Modifier}

import scala.collection.mutable.ListBuffer

object ActMove extends Action {

  override def stepsDuration: Int = 1

  override def action(modifiers: ListBuffer[Modifier]): Seq[Message] = {
    val modMoveDirection = Modifier.getFirst[ModMoveDirection](modifiers)
    val modPosition = Modifier.getFirst[ModPosition](modifiers)

    val propagationRadius = Modifier.getFirst[ModPositionPropagationRadius](modifiers).radius
    val representation = Modifier.getFirst[ModRepresentation](modifiers).representation
    val agentId = Modifier.getFirst[ModAgentIdentifier](modifiers).id
    val position = new Position(modMoveDirection.x, modMoveDirection.y) + modPosition.position

    modifiers -= modMoveDirection
    modifiers -= modPosition
    modifiers += ModPosition(position)

    Seq(new MesPosition(new CirclePropagation(position, propagationRadius), agentId, position, representation))
  }
}
