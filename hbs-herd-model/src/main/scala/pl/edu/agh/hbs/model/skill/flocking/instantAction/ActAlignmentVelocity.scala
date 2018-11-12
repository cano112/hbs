package pl.edu.agh.hbs.model.skill.flocking.instantAction

import pl.edu.agh.hbs.model
import pl.edu.agh.hbs.model.skill.Action
import pl.edu.agh.hbs.model.skill.common.modifier
import pl.edu.agh.hbs.model.skill.common.modifier.{ModNeighbour, ModVelocity}
import pl.edu.agh.hbs.model.skill.flocking.modifier.ModAlignmentVelocityParameters
import pl.edu.agh.hbs.model.{ModifierBuffer, StepOutput}

object ActAlignmentVelocity extends Action {

  override def action(modifiers: ModifierBuffer): StepOutput = {
    val velocity = modifiers.getFirst[ModVelocity]("standard").velocity
    val neighbours = modifiers.getAll[ModNeighbour]
    //    val alignmentFactor = modifiers.getFirst[ModActionParameters]("AlignmentVelocity").parameters.getOrElse("alignmentFactor", 0.5)
    val alignmentFactor = modifiers.getFirst[ModAlignmentVelocityParameters].alignmentFactor

    //alignment / schooling
    val alignmentVelocity = if (neighbours.nonEmpty) {
      val perceivedVelocity = neighbours.foldLeft(model.Vector())(_ + _.velocity) / neighbours.size
      (perceivedVelocity - velocity) * alignmentFactor
    } else model.Vector()

    modifiers.update(modifier.ModVelocity(alignmentVelocity, "alignment"))
    new StepOutput()
  }
}
