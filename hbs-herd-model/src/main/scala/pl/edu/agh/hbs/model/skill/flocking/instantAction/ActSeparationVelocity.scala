package pl.edu.agh.hbs.model.skill.flocking.instantAction

import pl.edu.agh.hbs.model
import pl.edu.agh.hbs.model.skill.Action
import pl.edu.agh.hbs.model.skill.basic.modifier.{ModPosition, ModSpecies}
import pl.edu.agh.hbs.model.skill.common.modifier
import pl.edu.agh.hbs.model.skill.common.modifier.ModNeighbour
import pl.edu.agh.hbs.model.skill.flocking.modifier.ModSeparationVelocityParameters
import pl.edu.agh.hbs.model.{ModifierBuffer, StepOutput}

object ActSeparationVelocity extends Action {

  override def action(modifiers: ModifierBuffer): StepOutput = {
    val position = modifiers.getFirst[ModPosition].position
    val minimalDistance = modifiers.getFirst[ModSeparationVelocityParameters].minimalDistance
    val separationFactor = modifiers.getFirst[ModSeparationVelocityParameters].separationFactor
    val species = modifiers.getFirst[ModSpecies].species
    val neighbours = modifiers.getAll[ModNeighbour]
      .filter(m => species.species.getClass.isAssignableFrom(m.species.species.getClass)
        || m.species.species.getClass.isAssignableFrom(species.species.getClass))

    //separation / avoidance
    val filtered = neighbours.filter(n => position.distance(n.position) < minimalDistance)
    val separationVelocity = if (filtered.nonEmpty) {
      filtered.foldLeft(model.Vector())(_ - _.position + position) * separationFactor
    } else model.Vector()

    modifiers.update(modifier.ModVelocity(separationVelocity, "separation"))
    new StepOutput()
  }
}
