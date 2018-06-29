package pl.edu.agh.hbs.model.skill.move.action

import pl.edu.agh.hbs.model.skill.basic.modifier.ModPosition
import pl.edu.agh.hbs.model.skill.move.modifier.ModMoveDirection
import pl.edu.agh.hbs.model.skill.{Action, Message, Modifier}

import scala.collection.mutable.ListBuffer

object ActMove extends Action {

  override def stepsDuration: Int = 1

  override def action(modifiers: ListBuffer[Modifier]): Seq[Message] = {
    val moveDirection = modifiers.collect { case a: ModMoveDirection => a }.head
    val position = modifiers.collect { case a: ModPosition => a }.head
    modifiers -= moveDirection
    modifiers -= position
    modifiers += new ModPosition(position.x + moveDirection.x, position.y + moveDirection.y)
    Seq.empty[Message]
  }
}
