package pl.edu.agh.hbs.model

import pl.edu.agh.hbs.model.skill.{Action, Decision, Message}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class CurrentActions {
  private val outMessages: ListBuffer[Message] = scala.collection.mutable.ListBuffer.empty[Message]
  private val currentActions = mutable.Queue[Action]()
  private var remainingSteps: Int = 0

  def shouldDecide: Boolean = !(remainingSteps > 0 || currentActions.nonEmpty)

  def updateQueue(decision: Decision): Unit = currentActions ++= decision.actions

  def step(modifiers: ModifierBuffer): Unit = {
    if (remainingSteps > 0) {
      remainingSteps -= 1
    } else if (currentActions.isEmpty) {
      takeInstantActions(modifiers)
      takeAction(modifiers)
      remainingSteps -= 1
    } else {
      takeInstantActions(modifiers)
      takeAction(modifiers)
    }
  }

  private def takeAction(modifiers: ModifierBuffer): Unit = {
    if (currentActions.nonEmpty) {
      val currentAction = currentActions.dequeue()
      remainingSteps = currentAction.stepsDuration
      outMessages ++= currentAction.action(modifiers)
    }
  }

  private def takeInstantActions(modifiers: ModifierBuffer): Unit = {
    while (currentActions.nonEmpty && currentActions.head.stepsDuration <= 0) {
      val currentAction = currentActions.dequeue()
      outMessages ++= currentAction.action(modifiers)
    }
  }

}
