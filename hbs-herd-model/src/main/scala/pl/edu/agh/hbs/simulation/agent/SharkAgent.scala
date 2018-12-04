package pl.edu.agh.hbs.simulation.agent

import pl.edu.agh.hbs.model
import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.skill.basic.modifier.{ModIdentifier, ModSpecies}
import pl.edu.agh.hbs.model.skill.common.modifier.ModVelocity
import pl.edu.agh.hbs.model.skill.dying.DyingAgent
import pl.edu.agh.hbs.model.skill.moving.MovingAgent
import pl.edu.agh.hbs.model.skill.predator.Predator
import pl.edu.agh.hbs.model.skill.predator.modifier.ModHuntFor
import pl.edu.agh.hbs.model.{Agent, ModifierBuffer}
import pl.edu.agh.hbs.simulation.species.{Fish, Shark}

import scala.collection.mutable.ListBuffer
import scala.util.Random

class SharkAgent(private val initModifiers: Seq[Modifier], inheritedModifiers: ModifierBuffer)
  extends Agent(initModifiers, inheritedModifiers)
    with MovingAgent
    with DyingAgent
    with Predator {

  override def modifiersCopiedFromParent(inherited: ModifierBuffer): Seq[Modifier] = {
    val modifiers = ListBuffer.empty[Modifier]
    super.modifiersCopiedFromParent(inherited) ++ modifiers
  }

  override def defaultModifiers(): Seq[Modifier] = {
    val modifiers = ListBuffer.empty[Modifier]
    modifiers += ModHuntFor(Fish)
    modifiers += ModSpecies(Shark)
    modifiers += ModIdentifier(Shark.nextId())
    modifiers += ModVelocity(model.Vector((new Random().nextDouble() - 0.5) * 20, (new Random().nextDouble() - 0.5) * 20), "current")
    super.defaultModifiers() ++ modifiers
  }
}
