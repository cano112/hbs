package pl.edu.agh.hbs.model.skill

import pl.edu.agh.hbs.model.ModifierBuffer

abstract class Decision extends Serializable {

  val actions: List[Action]

  def priority: Int

  def decision(modifiers: ModifierBuffer): Boolean

}
