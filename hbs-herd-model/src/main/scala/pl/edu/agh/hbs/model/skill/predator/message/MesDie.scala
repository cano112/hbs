package pl.edu.agh.hbs.model.skill.predator.message

import pl.edu.agh.hbs.model.ModifierBuffer
import pl.edu.agh.hbs.model.perception.DirectPerception
import pl.edu.agh.hbs.model.skill.Message
import pl.edu.agh.hbs.model.skill.basic.modifier.ModLifeStatus

class MesDie(val victimId: String) extends Message(perception = Seq(new DirectPerception(victimId))) {

  def process(modifiers: ModifierBuffer): Unit = {
    modifiers.update(ModLifeStatus(alive = false))
  }

}
