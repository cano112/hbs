package pl.edu.agh.hbs.simulation.agent

import pl.edu.agh.hbs.model.{Agent, ModifierBuffer}
import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.skill.basic.modifier.{ModIdentifier, ModSpecies}
import pl.edu.agh.hbs.model.skill.breeding.BreedingAgent
import pl.edu.agh.hbs.model.skill.common.modifier.ModEnergy
import pl.edu.agh.hbs.model.skill.dying.DyingAgent
import pl.edu.agh.hbs.model.skill.moving.MovingAgent
import pl.edu.agh.hbs.simulation.species.Fish

class FishAgent(private val initModifiers: Seq[Modifier], inheritedModifiers: ModifierBuffer)
  extends Agent(initModifiers, inheritedModifiers)
    with MovingAgent
    with BreedingAgent
    with DyingAgent {
  this.modifiers.update(ModSpecies(Fish))
  this.modifiers.update(ModIdentifier(Fish.nextId()))
  this.modifiers.update(ModEnergy(0, "consumed"))
}
