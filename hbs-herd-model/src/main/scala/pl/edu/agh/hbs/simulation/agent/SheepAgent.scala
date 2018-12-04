package pl.edu.agh.hbs.simulation.agent

import pl.edu.agh.hbs.model
import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.skill.basic.modifier.{ModIdentifier, ModSpecies}
import pl.edu.agh.hbs.model.skill.breeding.BreedingAgent
import pl.edu.agh.hbs.model.skill.common.modifier.{ModEnergy, ModVelocity}
import pl.edu.agh.hbs.model.skill.dying.DyingAgent
import pl.edu.agh.hbs.model.skill.flocking.FlockingAgent
import pl.edu.agh.hbs.model.skill.moving.MovingAgent
import pl.edu.agh.hbs.model.skill.prey.Prey
import pl.edu.agh.hbs.model.skill.prey.modifier.ModFearOf
import pl.edu.agh.hbs.model.{Agent, ModifierBuffer}
import pl.edu.agh.hbs.simulation.species.{Sheep, Wolf}

import scala.collection.mutable.ListBuffer
import scala.util.Random

class SheepAgent(private val initModifiers: Seq[Modifier], inheritedModifiers: ModifierBuffer)
  extends Agent(initModifiers, inheritedModifiers)
    with MovingAgent
    with FlockingAgent
    with BreedingAgent
    with DyingAgent
    with Prey {

  override def modifiersCopiedFromParent(inherited: ModifierBuffer): Seq[Modifier] = {
    val modifiers = ListBuffer.empty[Modifier]
    super.modifiersCopiedFromParent(inherited) ++ modifiers
  }

  override def defaultModifiers(): Seq[Modifier] = {
    val modifiers = ListBuffer.empty[Modifier]
    modifiers += ModFearOf(Wolf)
    modifiers += ModSpecies(Sheep)
    modifiers += ModIdentifier(Sheep.nextId())
    modifiers += ModEnergy(0, "consumed")
    modifiers += ModVelocity(model.Vector((new Random().nextDouble() - 0.5) * 20, (new Random().nextDouble() - 0.5) * 20), "wind")
    super.defaultModifiers() ++ modifiers
  }
}
