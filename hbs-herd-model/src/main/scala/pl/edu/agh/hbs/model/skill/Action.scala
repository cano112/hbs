package pl.edu.agh.hbs.model.skill

import scala.collection.mutable.ListBuffer

abstract class Action {

  def stepsDuration: Int

  def action(modifiers: ListBuffer[Modifier]): Unit

}
