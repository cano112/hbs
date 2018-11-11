package pl.edu.agh.hbs.model.skill.common.message

import pl.edu.agh.hbs.model.propagation.Propagation
import pl.edu.agh.hbs.model.skill.Message
import pl.edu.agh.hbs.model.skill.common.modifier
import pl.edu.agh.hbs.model.skill.common.modifier.ModNeighbour
import pl.edu.agh.hbs.model.{Agent, SpeciesObject, Vector}

class MesNeighbour(override val propagation: Propagation,
                   val senderId: String,
                   val species: SpeciesObject,
                   val position: Vector,
                   val velocity: Vector) extends Message(propagation) {

  def process(agent: Agent): Unit = {
    val modVisibleAgents = agent.modifiers
      .getAll[ModNeighbour]((mod: ModNeighbour) => senderId == mod.agentId)
    agent.modifiers -- modVisibleAgents
    if (propagation.shouldReceive(agent)) {
      agent.modifiers.update(modifier.ModNeighbour(senderId, species, position, velocity))
    }
  }
}
