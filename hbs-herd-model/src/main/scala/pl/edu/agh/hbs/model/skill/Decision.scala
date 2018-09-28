package pl.edu.agh.hbs.model.skill

import pl.edu.agh.hbs.model.modifier_cardinality.ModifierBuffer

abstract class Decision extends Serializable {

  def priority: Int

  def decision(modifiers: ModifierBuffer): Boolean

}
