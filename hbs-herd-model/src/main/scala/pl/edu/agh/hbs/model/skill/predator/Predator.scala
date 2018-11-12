package pl.edu.agh.hbs.model.skill.predator

import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.skill.predator.decision.DecHunt
import pl.edu.agh.hbs.model.skill.predator.modifier.ModHuntFor
import pl.edu.agh.hbs.model.{Agent, ModifierBuffer}

import scala.collection.mutable.ListBuffer

trait Predator extends Agent {
  this.decisions += DecHunt

  override def parametersCopiedForChild(modifiers: ModifierBuffer): Seq[Modifier] = {
    val initModifiers = ListBuffer.empty[Modifier]
    modifiers.getAll[ModHuntFor].foreach(m => initModifiers += m.copy())
    initModifiers ++ super.parametersCopiedForChild(modifiers)
  }
}
