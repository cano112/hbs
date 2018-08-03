package pl.edu.agh.hbs.model.skill.basic.message

import pl.edu.agh.hbs.core.providers.Representation
import pl.edu.agh.hbs.model.{Agent, Position}
import pl.edu.agh.hbs.model.propagation.Propagation
import pl.edu.agh.hbs.model.skill.{Message, Modifier}
import pl.edu.agh.hbs.model.skill.basic.modifier.ModVisibleAgent

class MesPosition(override val propagation: Propagation,
                  override val senderId: String,
                  val position: Position,
                  val representation: Representation) extends Message(propagation, senderId) {

  def process(agent: Agent): Unit = {
    val modVisibleAgents = Modifier
      .getAll[ModVisibleAgent]((mod: ModVisibleAgent) => senderId == mod.agentId)(agent.modifiers)
    agent.modifiers --= modVisibleAgents
    if(propagation.shouldReceive(agent)) {
        agent.modifiers += ModVisibleAgent(senderId, position, representation)
    }
  }
}
