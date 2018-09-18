package pl.edu.agh.hbs.model

import pl.edu.agh.hbs.core.providers.Representation
import pl.edu.agh.hbs.model.skill.basic.modifier.{ModAgentIdentifier, ModPosition, ModRepresentation}
import pl.edu.agh.hbs.model.skill.{Action, Decision, Message, Modifier}

import scala.collection.mutable.ListBuffer

abstract class Agent(private val initModifiers: Seq[Modifier]) extends Serializable {
  implicit val modifiers: ListBuffer[Modifier] = scala.collection.mutable.ListBuffer.empty[Modifier]
  protected val decisions: ListBuffer[Decision] = scala.collection.mutable.ListBuffer.empty[Decision]
  protected val actions: ListBuffer[Action] = scala.collection.mutable.ListBuffer.empty[Action]
  private val outMessages: ListBuffer[Message] = scala.collection.mutable.ListBuffer.empty[Message]
  modifiers ++= initModifiers
  protected var remainingSteps = 0

  def beforeStep(messages: Seq[Message]): Unit = {
    messages
      .filter(m => m.senderId != this.id)
      .foreach(m => m.process(this))
  }

  def step(): Unit = {
    if (remainingSteps <= 0) {
      val i = decide()
      remainingSteps = actions(i).stepsDuration
      takeAction(i)
    } else
      remainingSteps -= 1
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
    if (i >= 0) {
      val messages = actions(i).action(modifiers)
      outMessages ++= messages
    }
  }

  def position(): Vector = Modifier.getFirst[ModPosition](modifiers).position

  def representation(): Representation = Modifier.getFirst[ModRepresentation](modifiers).representation

  def id(): String = Modifier.getFirst[ModAgentIdentifier](modifiers).id

}
