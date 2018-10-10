package pl.edu.agh.hbs.model

import pl.edu.agh.hbs.core.providers.Representation
import pl.edu.agh.hbs.model.cardinality.ModifierBuffer
import pl.edu.agh.hbs.model.skill.basic.modifier.{ModAgentIdentifier, ModPosition, ModRepresentation, ModVelocity}
import pl.edu.agh.hbs.model.skill.{Action, Decision, Message, Modifier}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

abstract class Agent(private val initModifiers: Seq[Modifier]) extends Serializable {
  implicit val modifiers: ModifierBuffer = new ModifierBuffer()
  protected val decisions: ListBuffer[Decision] = scala.collection.mutable.ListBuffer.empty[Decision]
  private val outMessages: ListBuffer[Message] = scala.collection.mutable.ListBuffer.empty[Message]
  modifiers.update(initModifiers)
  private val actions = mutable.Queue[Action]()
  private var remainingSteps: Int = 0

  def beforeStep(messages: Seq[Message]): Unit = {
    messages.foreach(m => m.process(this))
  }

  def step(): Unit = {
    if (remainingSteps > 0) {
      remainingSteps -= 1
    } else if (actions.isEmpty) {
      val currentDecision = decide()
      actions ++= currentDecision.actions
      takeInstantActions()
      takeAction()
      remainingSteps -= 1
    } else {
      takeInstantActions()
      takeAction()
    }
  }

  def afterStep(): Seq[Message] = {
    val messages = outMessages.clone()
    outMessages.clear()
    messages
  }

  private def decide(): Decision = {
    var number = -1
    var priority = -1
    for ((decision, i) <- decisions.view.zipWithIndex) {
      if (decision.decision(modifiers) && decision.priority > priority) {
        number = i
        priority = decision.priority
      }
    }
    decisions(number)
  }

  private def takeAction(): Unit = {
    if (actions.nonEmpty) {
      val currentAction = actions.dequeue()
      remainingSteps = currentAction.stepsDuration
      val messages = currentAction.action(modifiers)
      outMessages ++= messages
    }
  }

  private def takeInstantActions(): Unit = {
    while (actions.nonEmpty && actions.head.stepsDuration <= 0) {
      val currentAction = actions.dequeue()
      val messages = currentAction.action(modifiers)
      outMessages ++= messages
    }
  }

  def position(): Vector = modifiers.getFirst[ModPosition].position

  def rotation(): Double = {
    val velocity = modifiers.getFirst[ModVelocity].velocity
    val angle =
      if (velocity(0) != 0) math.atan(velocity(1) / velocity(0)) * 180 / math.Pi
      else if (velocity(1) >= 0) 0
      else 180
    if (velocity(0) > 0)
      angle + 90
    else
      angle - 90
  }

  def representation(): Representation = modifiers.getFirst[ModRepresentation].representation

  def id(): String = modifiers.getFirst[ModAgentIdentifier].id

}
