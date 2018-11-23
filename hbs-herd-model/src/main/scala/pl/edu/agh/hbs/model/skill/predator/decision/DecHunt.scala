package pl.edu.agh.hbs.model.skill.predator.decision

import pl.edu.agh.hbs.model.ModifierBuffer
import pl.edu.agh.hbs.model.skill.basic.modifier.ModPosition
import pl.edu.agh.hbs.model.skill.common.modifier.ModNeighbour
import pl.edu.agh.hbs.model.skill.predator.action.ActHunt
import pl.edu.agh.hbs.model.skill.predator.modifier.{ModHuntFor, ModHuntParameters}
import pl.edu.agh.hbs.model.skill.{Action, Decision}

object DecHunt extends Decision {

  override val actions: List[Action] = List(ActHunt)

  override def priority: Int = 4

  override def decision(modifiers: ModifierBuffer): Boolean = {
    val preyCandidates = modifiers.getAll[ModHuntFor].map(m => m.prey)
    val position = modifiers.getFirst[ModPosition].position
    val preyDistance = modifiers.getFirst[ModHuntParameters].preyDistance

    val preys = modifiers.getAll[ModNeighbour]
      .filter(m => preyCandidates.exists(p => p.species.getClass.isAssignableFrom(m.species.species.getClass)))
      .filter(m => m.position.distance(position) < preyDistance)
    preys.nonEmpty
  }

}
