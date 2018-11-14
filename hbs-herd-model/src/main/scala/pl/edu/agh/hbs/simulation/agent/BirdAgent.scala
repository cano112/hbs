package pl.edu.agh.hbs.simulation.agent

import pl.edu.agh.hbs.model
import pl.edu.agh.hbs.model.Agent
import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.skill.basic.modifier.{ModIdentifier, ModSpecies}
import pl.edu.agh.hbs.model.skill.common.modifier.ModVelocity
import pl.edu.agh.hbs.model.skill.flocking.FlockingAgent
import pl.edu.agh.hbs.model.skill.moving.MovingAgent
import pl.edu.agh.hbs.simulation.species.Bird

class BirdAgent(private val initModifiers: Seq[Modifier])
  extends Agent(initModifiers)
    with MovingAgent
    with FlockingAgent {
  this.modifiers.update(ModSpecies(Bird))
  this.modifiers.update(ModIdentifier(Bird.nextId()))
  this.modifiers.update(ModVelocity(model.Vector(10.0, 0.0), "wind"))
}
