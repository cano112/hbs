package pl.edu.agh.hbs.model

import scala.collection.mutable.ListBuffer

abstract class Agent {
  protected val modifiers: ListBuffer[Modifier] = scala.collection.mutable.ListBuffer.empty[Modifier]
  protected val decisions: ListBuffer[Decision] = scala.collection.mutable.ListBuffer.empty[Decision]
  protected val actions: ListBuffer[Action] = scala.collection.mutable.ListBuffer.empty[Action]
}
