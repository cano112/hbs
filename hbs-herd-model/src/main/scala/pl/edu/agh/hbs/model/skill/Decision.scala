package pl.edu.agh.hbs.model.skill

import scala.collection.mutable.ListBuffer

abstract class Decision {

  def priority: Int

  def decision(modifiers: ListBuffer[Modifier]): Boolean

}
