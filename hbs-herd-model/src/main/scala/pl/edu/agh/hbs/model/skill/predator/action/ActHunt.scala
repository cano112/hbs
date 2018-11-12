package pl.edu.agh.hbs.model.skill.predator.action

import pl.edu.agh.hbs.model.propagation.CircularPropagation
import pl.edu.agh.hbs.model.skill.Action
import pl.edu.agh.hbs.model.skill.basic.modifier.ModPosition
import pl.edu.agh.hbs.model.skill.common.modifier.ModNeighbour
import pl.edu.agh.hbs.model.skill.dying.energy.ModEnergy
import pl.edu.agh.hbs.model.skill.predator.message.MesDie
import pl.edu.agh.hbs.model.skill.predator.modifier.ModHuntFor
import pl.edu.agh.hbs.model.{ModifierBuffer, StepOutput}

import scala.collection.mutable.ListBuffer

object ActHunt extends Action {

  override def stepsDuration: Int = 1

  override def action(modifiers: ModifierBuffer): StepOutput = {
    val energy = modifiers.getFirst[ModEnergy]("standard").energy
    val eatenEnergy = modifiers.getFirst[ModEnergy]("eaten").energy
    modifiers.update(ModEnergy(energy + eatenEnergy, "standard"))

    val preyCandidates = modifiers.getAll[ModHuntFor].map(m => m.prey)
    val position = modifiers.getFirst[ModPosition].position
    val preyId = modifiers.getAll[ModNeighbour]
      .filter(m => preyCandidates.exists(p => p.species.getClass.isAssignableFrom(m.getClass)))
      .map(m => (m, m.position.distance(position)))
      .minBy(m => m._2)._1.agentId

    new StepOutput(ListBuffer(new MesDie(new CircularPropagation(position, 1000), preyId)))

  }

}
