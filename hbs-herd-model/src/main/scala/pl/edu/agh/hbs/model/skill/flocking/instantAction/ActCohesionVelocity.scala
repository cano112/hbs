package pl.edu.agh.hbs.model.skill.flocking.instantAction

import pl.edu.agh.hbs.model
import pl.edu.agh.hbs.model.skill.Action
import pl.edu.agh.hbs.model.skill.basic.modifier.ModPosition
import pl.edu.agh.hbs.model.skill.common.modifier
import pl.edu.agh.hbs.model.skill.common.modifier.ModNeighbour
import pl.edu.agh.hbs.model.skill.flocking.modifier.ModCohesionVelocityParameters
import pl.edu.agh.hbs.model.{ModifierBuffer, StepOutput}

object ActCohesionVelocity extends Action {

  override def action(modifiers: ModifierBuffer): StepOutput = {
    val position = modifiers.getFirst[ModPosition].position
    val neighbours = modifiers.getAll[ModNeighbour]
    //    val cohesionFactor = modifiers.getFirst[ModActionParameters]("CohesionVelocity").parameters.getOrElse("cohesionFactor", 1 / 7.5)
    val cohesionFactor = modifiers.getFirst[ModCohesionVelocityParameters].cohesionFactor

    //cohesion / clumping
    val cohesionVelocity = if (neighbours.nonEmpty) {
      val perceivedCentreOfMass = neighbours.foldLeft(model.Vector())(_ + _.position) / neighbours.size
      (perceivedCentreOfMass - position) * cohesionFactor
    } else model.Vector()

    modifiers.update(modifier.ModVelocity(cohesionVelocity, "cohesion"))
    new StepOutput()
  }
}
