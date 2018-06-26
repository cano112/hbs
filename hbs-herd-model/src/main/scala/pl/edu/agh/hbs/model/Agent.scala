package pl.edu.agh.hbs.model

import pl.edu.agh.hbs.model.skill.{Action, Decision, Modifier}

import scala.collection.mutable.ListBuffer

abstract class Agent extends Serializable {
  protected val modifiers: ListBuffer[Modifier] = scala.collection.mutable.ListBuffer.empty[Modifier]
  protected val decisions: ListBuffer[Decision] = scala.collection.mutable.ListBuffer.empty[Decision]
  protected val actions: ListBuffer[Action] = scala.collection.mutable.ListBuffer.empty[Action]

  def decide: Int = {
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

  def takeAction(i: Int): Unit = {
    actions(i).action(modifiers)
  }

}
