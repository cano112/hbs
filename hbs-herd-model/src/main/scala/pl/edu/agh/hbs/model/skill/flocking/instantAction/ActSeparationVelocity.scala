package pl.edu.agh.hbs.model.skill.flocking.instantAction

import pl.edu.agh.hbs.model
import pl.edu.agh.hbs.model.skill.Action
import pl.edu.agh.hbs.model.skill.basic.modifier.ModPosition
import pl.edu.agh.hbs.model.skill.common.modifier
import pl.edu.agh.hbs.model.skill.common.modifier.ModNeighbour
import pl.edu.agh.hbs.model.skill.flocking.modifier.ModSeparationVelocityParameters
import pl.edu.agh.hbs.model.{ModifierBuffer, StepOutput}

object ActSeparationVelocity extends Action {

  override def action(modifiers: ModifierBuffer): StepOutput = {
    val position = modifiers.getFirst[ModPosition].position
    val neighbours = modifiers.getAll[ModNeighbour]
    //    val minimalDistance: Double = modifiers.getFirst[ModActionParameters]("SeparationVelocity").parameters.getOrElse("minimalDistance", 25)
    //    val separationFactor: Double = modifiers.getFirst[ModActionParameters]("SeparationVelocity").parameters.getOrElse("separationFactor", 1)
    val minimalDistance = modifiers.getFirst[ModSeparationVelocityParameters].minimalDistance
    val separationFactor = modifiers.getFirst[ModSeparationVelocityParameters].separationFactor

    //separation / avoidance
    val filtered = neighbours.filter(n => position.distance(n.position) < minimalDistance)
    val separationVelocity = if (filtered.nonEmpty) {
      filtered.foldLeft(model.Vector())(_ - _.position + position) * separationFactor
    } else model.Vector()

    modifiers.update(modifier.ModVelocity(separationVelocity, "separation"))
    new StepOutput()
  }
}
