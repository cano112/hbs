package pl.edu.agh.hbs.model.skill.prey

import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.skill.prey.modifier.ModFearOf
import pl.edu.agh.hbs.model.{Agent, ModifierBuffer}

import scala.collection.mutable.ListBuffer

trait Prey extends Agent {

  override def modifiersCopiedFromParent(inherited: ModifierBuffer): Seq[Modifier] = {
    val modifiers = ListBuffer.empty[Modifier]
    inherited.getAll[ModFearOf].foreach(m => modifiers += m.copy())
    super.modifiersCopiedFromParent(inherited) ++ modifiers
  }
}
