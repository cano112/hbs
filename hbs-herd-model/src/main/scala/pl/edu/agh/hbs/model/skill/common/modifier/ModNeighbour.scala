package pl.edu.agh.hbs.model.skill.common.modifier

import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.{SpeciesObject, Vector}

case class ModNeighbour(agentId: String, species: SpeciesObject, position: Vector, velocity: Vector) extends Modifier(agentId) {
  override def copy(): Modifier = ModNeighbour(agentId, species, position, velocity)
}
