package pl.edu.agh.hbs.model.skill

import pl.edu.agh.hbs.model.cardinality.ModifierBuffer

abstract class Action extends Serializable {

  def stepsDuration: Int

  def action(modifiers: ModifierBuffer): Seq[Message]

}
