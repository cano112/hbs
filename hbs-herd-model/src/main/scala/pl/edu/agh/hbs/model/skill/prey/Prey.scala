package pl.edu.agh.hbs.model.skill.prey

import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.skill.common.modifier.ModEnergy
import pl.edu.agh.hbs.model.skill.prey.modifier.ModFearOf
import pl.edu.agh.hbs.model.{Agent, ModifierBuffer}

import scala.collection.mutable.ListBuffer

trait Prey extends Agent {

  override def modifiersCopiedForChild(modifiers: ModifierBuffer): Seq[Modifier] = {
    val initModifiers = ListBuffer.empty[Modifier]
    modifiers.getAll[ModFearOf].foreach(m => initModifiers += m.copy())
    initModifiers ++ super.modifiersCopiedForChild(modifiers)
  }
}
