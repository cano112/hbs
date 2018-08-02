package pl.edu.agh.hbs.model.skill.move.action

import pl.edu.agh.hbs.model.Position
import pl.edu.agh.hbs.model.propagation.CirclePropagation
import pl.edu.agh.hbs.model.skill.basic.message.MesPosition
import pl.edu.agh.hbs.model.skill.basic.modifier.{ModPosition, ModPositionPropagationRadius}
import pl.edu.agh.hbs.model.skill.move.modifier.ModMoveDirection
import pl.edu.agh.hbs.model.skill.{Action, Message, Modifier}

import scala.collection.mutable.ListBuffer

object ActMove extends Action {

  override def stepsDuration: Int = 1

  override def action(modifiers: ListBuffer[Modifier]): Seq[Message] = {
    val modMoveDirection = modifiers.collect { case a: ModMoveDirection => a }.head
    val modPosition = modifiers.collect { case a: ModPosition => a }.head
    val modPositionPropagationRadius = modifiers.collect { case a: ModPositionPropagationRadius => a }.head
    val position = new Position(modMoveDirection.x, modMoveDirection.y) + modPosition.position

    modifiers -= modMoveDirection
    modifiers -= modPosition
    modifiers += new ModPosition(position)

    Seq(new MesPosition(new CirclePropagation(position, modPositionPropagationRadius.radius), position))
  }
}
