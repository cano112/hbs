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

  override def defaultInitModifiers(): Seq[Modifier] = {
    val initModifiers = ListBuffer.empty[Modifier]
    initModifiers += ModSpecies(Bird)
    initModifiers += ModIdentifier(Bird.nextId())
    initModifiers += ModVelocity(model.Vector(10.0, 0.0), "wind")
    initModifiers ++ super.defaultInitModifiers()
  }
}
