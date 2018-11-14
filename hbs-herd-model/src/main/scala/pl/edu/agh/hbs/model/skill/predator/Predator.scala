package pl.edu.agh.hbs.model.skill.predator

import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.skill.dying.energy.ModEnergy
import pl.edu.agh.hbs.model.skill.predator.decision.DecHunt
import pl.edu.agh.hbs.model.skill.predator.modifier.ModHuntFor
import pl.edu.agh.hbs.model.{Agent, ModifierBuffer}

import scala.collection.mutable.ListBuffer

trait Predator extends Agent {
  this.decisions += DecHunt
  this.modifiers.update(ModEnergy(400, "standard"))
  this.modifiers.update(ModEnergy(50, "eaten"))
  this.modifiers.update(ModEnergy(1, "consumed"))

  override def parametersCopiedForChild(modifiers: ModifierBuffer): Seq[Modifier] = {
    val initModifiers = ListBuffer.empty[Modifier]
    modifiers.getAll[ModHuntFor].foreach(m => initModifiers += m.copy())
    initModifiers += modifiers.getFirst[ModEnergy]("consumed").copy()
    initModifiers ++ super.parametersCopiedForChild(modifiers)
  }
}
