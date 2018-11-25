package pl.edu.agh.hbs.model.skill.prey.instantAction

import pl.edu.agh.hbs.model
import pl.edu.agh.hbs.model.skill.Action
import pl.edu.agh.hbs.model.skill.basic.modifier.ModPosition
import pl.edu.agh.hbs.model.skill.common.modifier.{ModNeighbour, ModVelocity}
import pl.edu.agh.hbs.model.skill.prey.modifier.{ModFearOf, ModRunAwayFromPredatorParameters}
import pl.edu.agh.hbs.model.{ModifierBuffer, StepOutput}

object ActRunAwayFromPredator extends Action {

  override def action(modifiers: ModifierBuffer): StepOutput = {
    val predatorCandidates = modifiers.getAll[ModFearOf].map(m => m.predator)
    val position = modifiers.getFirst[ModPosition].position
    val predatorFactor = modifiers.getFirst[ModRunAwayFromPredatorParameters].predatorFactor
    val predators = modifiers.getAll[ModNeighbour]
      .filter(m => predatorCandidates.exists(p => p.species.getClass.isAssignableFrom(m.species.species.getClass)))

    val predatorVelocity = if (predators.nonEmpty) {
      val perceivedCentreOfMass = predators.foldLeft(model.Vector())(_ + _.position) / predators.size
      (perceivedCentreOfMass - position) * predatorFactor
    } else model.Vector()

    modifiers.update(ModVelocity(predatorVelocity, "predator"))
    new StepOutput()
  }
}
