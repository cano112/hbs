package pl.edu.agh.hbs.model.skill.predator

import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.skill.common.modifier.ModEnergy
import pl.edu.agh.hbs.model.skill.predator.decision.DecHunt
import pl.edu.agh.hbs.model.skill.predator.modifier.{ModHuntFor, ModHuntParameters}
import pl.edu.agh.hbs.model.{Agent, ModifierBuffer}

import scala.collection.mutable.ListBuffer

trait Predator extends Agent {
  this.decisions += DecHunt

  override def modifiersCopiedFromParent(inherited: ModifierBuffer): Seq[Modifier] = {
    val modifiers = ListBuffer.empty[Modifier]
    inherited.getAll[ModHuntFor].foreach(m => modifiers += m.copy())
    inherited.getAll[ModEnergy](Seq("eaten")).foreach(m => modifiers += m.copy())
    inherited.getAll[ModHuntParameters].foreach(m => modifiers += m.copy())
    super.modifiersCopiedFromParent(inherited) ++ modifiers
  }

  override def defaultModifiers(): Seq[Modifier] = {
    val modifiers = ListBuffer.empty[Modifier]
    modifiers += ModEnergy(400, "standard")
    modifiers += ModEnergy(50, "eaten")
    modifiers += ModHuntParameters(100)
    super.defaultModifiers() ++ modifiers
  }
}
