package pl.edu.agh.hbs.model.skill.prey

import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.skill.prey.instantAction.ActRunAwayFromPredator
import pl.edu.agh.hbs.model.skill.prey.modifier.{ModFearOf, ModRunAwayFromPredatorParameters}
import pl.edu.agh.hbs.model.{Agent, ModifierBuffer}

import scala.collection.mutable.ListBuffer

trait Prey extends Agent {
  this.beforeStepActions += ActRunAwayFromPredator

  override def modifiersCopiedFromParent(inherited: ModifierBuffer): Seq[Modifier] = {
    val modifiers = ListBuffer.empty[Modifier]
    inherited.getAll[ModFearOf].foreach(m => modifiers += m.copy())
    inherited.getAll[ModRunAwayFromPredatorParameters].foreach(m => modifiers += m.copy())
    super.modifiersCopiedFromParent(inherited) ++ modifiers
  }

  override def defaultModifiers(): Seq[Modifier] = {
    val modifiers = ListBuffer.empty[Modifier]
    modifiers += ModRunAwayFromPredatorParameters(0.2)
    super.defaultModifiers() ++ modifiers
  }
}
