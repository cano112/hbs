package pl.edu.agh.hbs.model.skill.predator.message

import pl.edu.agh.hbs.model.Agent
import pl.edu.agh.hbs.model.perception.DirectPerception
import pl.edu.agh.hbs.model.propagation.AllPropagation
import pl.edu.agh.hbs.model.skill.Message
import pl.edu.agh.hbs.model.skill.basic.modifier.ModLifeStatus

class MesDie(val victimId: String) extends Message(new AllPropagation(), Seq(new DirectPerception(victimId))) {

  def process(agent: Agent): Unit = {
    if (perception.forall(p => p.shouldProcess(agent))) {
      agent.modifiers.update(ModLifeStatus(alive = false))
    }
  }
}
