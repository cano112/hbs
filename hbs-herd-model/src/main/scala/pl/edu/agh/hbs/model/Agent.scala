package pl.edu.agh.hbs.model

import pl.edu.agh.hbs.core.providers.Representation
import pl.edu.agh.hbs.model.skill.basic.modifier._
import pl.edu.agh.hbs.model.skill.{Action, Decision, Message, Modifier}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

abstract class Agent(private val initModifiers: Seq[Modifier]) extends Serializable {
  val modifiers: ModifierBuffer = new ModifierBuffer()
  protected val decisions: ListBuffer[Decision] = scala.collection.mutable.ListBuffer.empty[Decision]
  protected val beforeStepActions: ListBuffer[Action] = scala.collection.mutable.ListBuffer.empty[Action]
  protected val afterStepActions: ListBuffer[Action] = scala.collection.mutable.ListBuffer.empty[Action]

  private val outMessages: ListBuffer[Message] = scala.collection.mutable.ListBuffer.empty[Message]
  private val currentActions = mutable.Queue[Action]()
  private var remainingSteps: Int = 0

  modifiers.update(ModLifeStatus() +: initModifiers)

  def beforeStep(messages: Seq[Message]): Unit = {
    messages.foreach(m => m.process(this))
    beforeStepActions.foreach(a => outMessages ++= a.action(modifiers))
  }

  def step(): Unit = {
    if (remainingSteps > 0) {
      remainingSteps -= 1
    } else if (currentActions.isEmpty) {
      val currentDecision = decide()
      currentActions ++= currentDecision.actions
      takeInstantActions()
      takeAction()
      remainingSteps -= 1
    } else {
      takeInstantActions()
      takeAction()
    }
  }

  def afterStep(): Seq[Message] = {
    afterStepActions.foreach(a => outMessages ++= a.action(modifiers))
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
    if (currentActions.nonEmpty) {
      val currentAction = currentActions.dequeue()
      remainingSteps = currentAction.stepsDuration
      outMessages ++= currentAction.action(modifiers)
    }
  }

  private def takeInstantActions(): Unit = {
    while (currentActions.nonEmpty && currentActions.head.stepsDuration <= 0) {
      val currentAction = currentActions.dequeue()
      outMessages ++= currentAction.action(modifiers)
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

  def alive(): Boolean = modifiers.getFirst[ModLifeStatus].alive

}
