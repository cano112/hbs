package pl.edu.agh.hbs.simulation.agent

import pl.edu.agh.hbs.model
import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.skill.basic.modifier.{ModIdentifier, ModSpecies}
import pl.edu.agh.hbs.model.skill.common.modifier.ModVelocity
import pl.edu.agh.hbs.model.skill.flocking.FlockingAgent
import pl.edu.agh.hbs.model.skill.moving.MovingAgent
import pl.edu.agh.hbs.model.{Agent, ModifierBuffer}
import pl.edu.agh.hbs.simulation.species.Bird

import scala.collection.mutable.ListBuffer

class BirdAgent(private val initModifiers: Seq[Modifier], inheritedModifiers: ModifierBuffer)
  extends Agent(initModifiers, inheritedModifiers)
    with MovingAgent
    with FlockingAgent {

  override def defaultModifiers(): Seq[Modifier] = {
    val modifiers = ListBuffer.empty[Modifier]
    modifiers += ModSpecies(Bird)
    modifiers += ModIdentifier(Bird.nextId())
    modifiers += ModVelocity(model.Vector(10.0, 0.0), "wind")
    super.defaultModifiers() ++ modifiers
  }
}
