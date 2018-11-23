package pl.edu.agh.hbs.model.skill.breeding

import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.skill.breeding.decision.DecBreed
import pl.edu.agh.hbs.model.skill.breeding.modifier.ModBreedParameters
import pl.edu.agh.hbs.model.skill.common.modifier.ModTimer
import pl.edu.agh.hbs.model.{Agent, ModifierBuffer}

import scala.collection.mutable.ListBuffer

trait BreedingAgent extends Agent {
  this.decisions += DecBreed
  this.modifiers.update(ModBreedParameters(4, 1000))
  this.modifiers.update(ModTimer(0, "breed"))

  override def modifiersCopiedForChild(modifiers: ModifierBuffer): Seq[Modifier] = {
    val initModifiers = ListBuffer.empty[Modifier]
    modifiers.getAll[ModBreedParameters].foreach(m => initModifiers += m.copy())
    initModifiers ++ super.modifiersCopiedForChild(modifiers)
  }
}
