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

  override def modifiersCopiedFromParent(inherited: ModifierBuffer): Seq[Modifier] = {
    val modifiers = ListBuffer.empty[Modifier]
    inherited.getAll[ModEnergy](Seq("consumed")).foreach(m => modifiers += m.copy())
    super.modifiersCopiedFromParent(inherited) ++ modifiers
  }

  override def defaultModifiers(): Seq[Modifier] = {
    val modifiers = ListBuffer.empty[Modifier]
    modifiers += ModEnergy(100, "standard")
    modifiers += ModEnergy(1, "consumed")
    super.defaultModifiers() ++ modifiers
  }
}
