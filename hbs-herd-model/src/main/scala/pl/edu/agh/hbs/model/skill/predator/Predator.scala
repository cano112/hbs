package pl.edu.agh.hbs.model.skill.predator

import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.skill.common.modifier.ModEnergy
import pl.edu.agh.hbs.model.skill.predator.decision.DecHunt
import pl.edu.agh.hbs.model.skill.predator.modifier.{ModHuntFor, ModHuntParameters}
import pl.edu.agh.hbs.model.{Agent, ModifierBuffer}

import scala.collection.mutable.ListBuffer

trait Predator extends Agent {
  this.decisions += DecHunt

  override def modifiersCopiedForChild(modifiers: ModifierBuffer): Seq[Modifier] = {
    val initModifiers = ListBuffer.empty[Modifier]
    modifiers.getAll[ModHuntFor].foreach(m => initModifiers += m.copy())
    modifiers.getAll[ModEnergy](Seq("eaten")).foreach(m => initModifiers += m.copy())
    modifiers.getAll[ModHuntParameters].foreach(m => initModifiers += m.copy())
    initModifiers ++ super.modifiersCopiedForChild(modifiers)
  }

  override def defaultInitModifiers(): Seq[Modifier] = {
    val initModifiers = ListBuffer.empty[Modifier]
    initModifiers += ModEnergy(400, "standard")
    initModifiers += ModEnergy(50, "eaten")
    initModifiers += ModHuntParameters(100)
    initModifiers ++ super.defaultInitModifiers()
  }
}
