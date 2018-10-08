package pl.edu.agh.hbs.model.skill.basic.message

import pl.edu.agh.hbs.model.propagation.Propagation
import pl.edu.agh.hbs.model.skill.Message
import pl.edu.agh.hbs.model.skill.basic.modifier.ModVisibleAgent
import pl.edu.agh.hbs.model.{Agent, Vector}

class MesVisibleAgent(override val propagation: Propagation,
                      override val senderId: String,
                      val position: Vector,
                      val velocity: Vector) extends Message(propagation, senderId) {

  def process(agent: Agent): Unit = {
    val modVisibleAgents = agent.modifiers
      .getAll[ModVisibleAgent]((mod: ModVisibleAgent) => senderId == mod.agentId)
    agent.modifiers -- modVisibleAgents
    if (propagation.shouldReceive(agent)) {
      agent.modifiers.update(ModVisibleAgent(senderId, position, velocity))
    }
  }
}
