package pl.edu.agh.hbs.model.skill.dying

import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.skill.common.modifier.ModEnergy
import pl.edu.agh.hbs.model.skill.dying.decision.{DecDie, DecIsDead}
import pl.edu.agh.hbs.model.skill.dying.instantAction.ActConsumeEnergy
import pl.edu.agh.hbs.model.{Agent, ModifierBuffer}

import scala.collection.mutable.ListBuffer

trait DyingAgent extends Agent {
  this.decisions += DecDie
  this.decisions += DecIsDead
  this.afterStepActions += ActConsumeEnergy
  this.modifiers.update(ModEnergy(100, "standard"))
  this.modifiers.update(ModEnergy(1, "consumed"))

  override def modifiersCopiedForChild(modifiers: ModifierBuffer): Seq[Modifier] = {
    val initModifiers = ListBuffer.empty[Modifier]
    modifiers.getAll[ModEnergy](Seq("standard", "consumed")).foreach(m => initModifiers += m.copy())
    initModifiers ++ super.modifiersCopiedForChild(modifiers)
  }
}
