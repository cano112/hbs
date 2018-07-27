package pl.edu.agh.hbs.model.skill

import scala.collection.mutable.ListBuffer

abstract class Action extends Serializable {

  def stepsDuration: Int

  def action(modifiers: ListBuffer[Modifier]): Seq[Message]

}
