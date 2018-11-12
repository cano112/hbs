package pl.edu.agh.hbs.model.skill.predator.message

import pl.edu.agh.hbs.model.Agent
import pl.edu.agh.hbs.model.perception.DirectPerception
import pl.edu.agh.hbs.model.propagation.Propagation
import pl.edu.agh.hbs.model.skill.Message
import pl.edu.agh.hbs.model.skill.basic.modifier.ModLifeStatus

class MesDie(override val propagation: Propagation,
             val victimId: String) extends Message(propagation, Seq(new DirectPerception(victimId))) {

  def process(agent: Agent): Unit = {
    if (perception.forall(p => p.shouldProcess(agent))) {
      agent.modifiers.update(ModLifeStatus(alive = false))
    }
  }
}
