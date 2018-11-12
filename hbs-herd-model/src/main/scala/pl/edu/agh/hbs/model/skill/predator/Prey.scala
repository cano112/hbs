package pl.edu.agh.hbs.model.skill.predator

import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.skill.dying.energy.ModEnergy
import pl.edu.agh.hbs.model.skill.predator.modifier.ModFearOf
import pl.edu.agh.hbs.model.{Agent, ModifierBuffer}

import scala.collection.mutable.ListBuffer

trait Prey extends Agent {
  this.modifiers.update(ModEnergy(200, "standard"))
  this.modifiers.update(ModEnergy(1, "consumed"))

  override def parametersCopiedForChild(modifiers: ModifierBuffer): Seq[Modifier] = {
    val initModifiers = ListBuffer.empty[Modifier]
    modifiers.getAll[ModFearOf].foreach(m => initModifiers += m.copy())
    initModifiers ++ super.parametersCopiedForChild(modifiers)
  }
}
