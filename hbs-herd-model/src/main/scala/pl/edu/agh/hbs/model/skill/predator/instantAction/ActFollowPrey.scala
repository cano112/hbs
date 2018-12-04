package pl.edu.agh.hbs.model.skill.predator.instantAction

import pl.edu.agh.hbs.model
import pl.edu.agh.hbs.model.skill.Action
import pl.edu.agh.hbs.model.skill.basic.modifier.ModPosition
import pl.edu.agh.hbs.model.skill.common.modifier.{ModNeighbour, ModVelocity}
import pl.edu.agh.hbs.model.skill.predator.modifier.{ModFollowPreyParameters, ModHuntFor}
import pl.edu.agh.hbs.model.{ModifierBuffer, StepOutput}

object ActFollowPrey extends Action {

  override def action(modifiers: ModifierBuffer): StepOutput = {
    val preyCandidates = modifiers.getAll[ModHuntFor].map(m => m.prey)
    val position = modifiers.getFirst[ModPosition].position
    val followFactor = modifiers.getFirst[ModFollowPreyParameters].followFactor

    val preys = modifiers.getAll[ModNeighbour]
      .filter(m => preyCandidates.exists(p => p.species.getClass.isAssignableFrom(m.species.species.getClass)))

    val followVelocity = if (preys.nonEmpty) {
      val closestPreyPosition = preys
        .map(m => (m, m.position.distance(position)))
        .minBy(m => m._2)._1.position
      (closestPreyPosition - position) * followFactor
    } else model.Vector()

    modifiers.update(ModVelocity(followVelocity, "prey"))
    new StepOutput()
  }
}
