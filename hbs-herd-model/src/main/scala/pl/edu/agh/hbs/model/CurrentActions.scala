package pl.edu.agh.hbs.model

import pl.edu.agh.hbs.model.skill.{Action, Decision, Message}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class CurrentActions extends Serializable {
  private val stepOutput: StepOutput = new StepOutput()
  private val currentActions = mutable.Queue[Action]()
  private var remainingSteps: Int = 0

  def shouldDecide: Boolean = !(remainingSteps > 0 || currentActions.nonEmpty)

  def updateQueue(decision: Decision): Unit = currentActions ++= decision.actions

  def step(modifiers: ModifierBuffer): StepOutput = {
    if (remainingSteps > 0) {
    } else if (currentActions.isEmpty) {
      takeInstantActions(modifiers)
      takeAction(modifiers)
    } else {
      takeInstantActions(modifiers)
      takeAction(modifiers)
    }
    remainingSteps -= 1
    stepOutput.clearReturn()
  }

  private def takeAction(modifiers: ModifierBuffer): Unit = {
    if (currentActions.nonEmpty) {
      val currentAction = currentActions.dequeue()
      remainingSteps = currentAction.stepsDuration
      stepOutput += currentAction.action(modifiers)
    }
  }

  private def takeInstantActions(modifiers: ModifierBuffer): Unit = {
    while (currentActions.nonEmpty && currentActions.head.stepsDuration <= 0) {
      val currentAction = currentActions.dequeue()
      stepOutput += currentAction.action(modifiers)
    }
  }

}
