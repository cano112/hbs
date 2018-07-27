package pl.edu.agh.hbs.model.skill.move.action

import pl.edu.agh.hbs.model.Position
import pl.edu.agh.hbs.model.skill.basic.modifier.ModPosition
import pl.edu.agh.hbs.model.skill.move.modifier.ModMoveDirection
import pl.edu.agh.hbs.model.skill.{Action, Message, Modifier}

import scala.collection.mutable.ListBuffer

object ActMove extends Action {

  override def stepsDuration: Int = 1

  override def action(modifiers: ListBuffer[Modifier]): Seq[Message] = {
    val modMoveDirection = modifiers.collect { case a: ModMoveDirection => a }.head
    val modPosition = modifiers.collect { case a: ModPosition => a }.head
    modifiers -= modMoveDirection
    modifiers -= modPosition
    modifiers += new ModPosition(new Position(modMoveDirection.x, modMoveDirection.y) + modPosition.position)
    Seq.empty[Message]
  }
}
