package pl.edu.agh.hbs.model

import pl.edu.agh.hbs.core.providers.Representation
import pl.edu.agh.hbs.model.representation.elm.shape.BoxShape
import pl.edu.agh.hbs.model.skill.basic.modifier.{ModPosition, ModRepresentation}
import pl.edu.agh.hbs.model.skill.{Action, Decision, Message, Modifier}

import scala.collection.mutable.ListBuffer

abstract class Agent(private val initModifiers: Seq[Modifier]) extends Serializable {
  val modifiers: ListBuffer[Modifier] = scala.collection.mutable.ListBuffer.empty[Modifier]
  protected val decisions: ListBuffer[Decision] = scala.collection.mutable.ListBuffer.empty[Decision]
  protected val actions: ListBuffer[Action] = scala.collection.mutable.ListBuffer.empty[Action]
  private val outMessages: ListBuffer[Message] = scala.collection.mutable.ListBuffer.empty[Message]
  modifiers ++= initModifiers

  def beforeStep(messages: Seq[Message]): Unit = {
    messages.foreach(m => m.process(this))
  }

  def step(): Unit = {
    takeAction(decide())
  }

  def afterStep(): Seq[Message] = {
    val messages = outMessages.clone()
    outMessages.clear()
    messages
  }

  private def decide(): Int = {
    var number = -1
    var priority = -1
    for ((decision, i) <- decisions.view.zipWithIndex) {
      if (decision.decision(modifiers) && decision.priority > priority) {
        number = i
        priority = decision.priority
      }
    }
    number
  }

  private def takeAction(i: Int): Unit = {
    val messages = actions(i).action(modifiers)
    outMessages ++= messages
    BoxShape(1)
  }

  def position(): Position = {
    modifiers.collect { case a: ModPosition => a }.head.position
  }

  def representation(): Representation = {
    modifiers.collect { case a: ModRepresentation => a }.head.representation
  }

}
