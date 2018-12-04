package pl.edu.agh.hbs.model.skill.dying.message

import pl.edu.agh.hbs.model.ModifierBuffer
import pl.edu.agh.hbs.model.skill.Message
import pl.edu.agh.hbs.model.skill.common.modifier.ModNeighbour

class MesIsDead(val senderId: String) extends Message() {

  def process(modifiers: ModifierBuffer): Unit = {
    modifiers -- modifiers.getAll[ModNeighbour](Seq(senderId)) // works even if empty
  }

}
