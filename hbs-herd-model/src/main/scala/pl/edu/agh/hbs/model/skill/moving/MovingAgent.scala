package pl.edu.agh.hbs.model.skill.moving

import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.skill.moving.decision.DecMove
import pl.edu.agh.hbs.model.skill.moving.modifier.ModMoveParameters
import pl.edu.agh.hbs.model.{Agent, ModifierBuffer}

import scala.collection.mutable.ListBuffer

trait MovingAgent extends Agent {
  this.decisions += DecMove

  override def modifiersCopiedFromParent(inherited: ModifierBuffer): Seq[Modifier] = {
    val modifiers = ListBuffer.empty[Modifier]
    inherited.getAll[ModMoveParameters].foreach(m => modifiers += m.copy())
    super.modifiersCopiedFromParent(inherited) ++ modifiers
  }

  override def defaultModifiers(): Seq[Modifier] = {
    val modifiers = ListBuffer.empty[Modifier]
    modifiers += ModMoveParameters(0.1, 1000, 50)
    super.defaultModifiers() ++ modifiers
  }
}
