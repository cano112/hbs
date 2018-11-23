package pl.edu.agh.hbs.model.skill.moving

import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.skill.moving.decision.DecMove
import pl.edu.agh.hbs.model.skill.moving.modifier.ModMoveParameters
import pl.edu.agh.hbs.model.{Agent, ModifierBuffer}

import scala.collection.mutable.ListBuffer

trait MovingAgent extends Agent {
  this.decisions += DecMove

  override def modifiersCopiedForChild(modifiers: ModifierBuffer): Seq[Modifier] = {
    val initModifiers = ListBuffer.empty[Modifier]
    modifiers.getAll[ModMoveParameters].foreach(m => initModifiers += m.copy())
    initModifiers ++ super.modifiersCopiedForChild(modifiers)
  }

  override def defaultInitModifiers(): Seq[Modifier] = {
    val initModifiers = ListBuffer.empty[Modifier]
    initModifiers += ModMoveParameters(0.1, 1000, 50)
    initModifiers ++ super.defaultInitModifiers()
  }
}
