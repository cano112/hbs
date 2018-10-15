package pl.edu.agh.hbs.model.skill

import pl.edu.agh.hbs.model.ModifierBuffer

abstract class Action extends Serializable {

  def stepsDuration: Int = 0

  def action(modifiers: ModifierBuffer): Seq[Message]

}
