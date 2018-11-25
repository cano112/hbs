package pl.edu.agh.hbs.model.skill.flocking.instantAction

import pl.edu.agh.hbs.model
import pl.edu.agh.hbs.model.skill.Action
import pl.edu.agh.hbs.model.skill.basic.modifier.{ModPosition, ModSpecies}
import pl.edu.agh.hbs.model.skill.common.modifier.{ModNeighbour, ModVelocity}
import pl.edu.agh.hbs.model.skill.flocking.modifier.ModCohesionVelocityParameters
import pl.edu.agh.hbs.model.{ModifierBuffer, StepOutput}

object ActCohesionVelocity extends Action {

  override def action(modifiers: ModifierBuffer): StepOutput = {
    val position = modifiers.getFirst[ModPosition].position
    val cohesionFactor = modifiers.getFirst[ModCohesionVelocityParameters].cohesionFactor
    val species = modifiers.getFirst[ModSpecies].species
    val neighbours = modifiers.getAll[ModNeighbour]
      .filter(m => species.species.getClass.isAssignableFrom(m.species.species.getClass)
        || m.species.species.getClass.isAssignableFrom(species.species.getClass))

    //cohesion / clumping
    val cohesionVelocity = if (neighbours.nonEmpty) {
      val perceivedCentreOfMass = neighbours.foldLeft(model.Vector())(_ + _.position) / neighbours.size
      (perceivedCentreOfMass - position) * cohesionFactor
    } else model.Vector()

    modifiers.update(ModVelocity(cohesionVelocity, "cohesion"))
    new StepOutput()
  }
}
