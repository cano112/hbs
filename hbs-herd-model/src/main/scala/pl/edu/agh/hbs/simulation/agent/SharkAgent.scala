package pl.edu.agh.hbs.simulation.agent

import pl.edu.agh.hbs.model.Agent
import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.skill.basic.modifier.{ModIdentifier, ModSpecies}
import pl.edu.agh.hbs.model.skill.dying.DyingAgent
import pl.edu.agh.hbs.model.skill.moving.MovingAgent
import pl.edu.agh.hbs.model.skill.predator.Predator
import pl.edu.agh.hbs.model.skill.predator.modifier.ModHuntFor
import pl.edu.agh.hbs.simulation.species.{Fish, Shark}

class SharkAgent(private val initModifiers: Seq[Modifier])
  extends Agent(initModifiers)
    with MovingAgent
    with DyingAgent
    with Predator {
  this.modifiers.update(ModSpecies(Shark))
  this.modifiers.update(ModIdentifier(Shark.nextId()))
  this.modifiers.update(ModHuntFor(Fish))
}
