package pl.edu.agh.hbs.model.skill.predator.decision

import pl.edu.agh.hbs.model.ModifierBuffer
import pl.edu.agh.hbs.model.skill.common.modifier.ModNeighbour
import pl.edu.agh.hbs.model.skill.predator.modifier.ModHuntFor
import pl.edu.agh.hbs.model.skill.{Action, Decision}

object DecLookForPrey extends Decision {

  override val actions: List[Action] = List()

  override def priority: Int = 4

  override def decision(modifiers: ModifierBuffer): Boolean = {
    val preyCandidates = modifiers.getAll[ModHuntFor].map(m => m.prey)
    val preys = modifiers.getAll[ModNeighbour]
      .filter(m => preyCandidates.exists(p => p.species.getClass.isAssignableFrom(m.getClass)))
    preys.nonEmpty
  }
}
