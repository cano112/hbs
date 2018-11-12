package pl.edu.agh.hbs.model.skill.moving.instantAction

import pl.edu.agh.hbs.model.skill.Action
import pl.edu.agh.hbs.model.skill.common.modifier
import pl.edu.agh.hbs.model.skill.common.modifier.ModVelocity
import pl.edu.agh.hbs.model.skill.moving.modifier.ModRecalculateVelocityParameters
import pl.edu.agh.hbs.model.{ModifierBuffer, StepOutput}

object ActRecalculateVelocity extends Action {

  override def action(modifiers: ModifierBuffer): StepOutput = {
    var velocity = modifiers.getAll[ModVelocity].map(m => m.velocity).reduce((m, acc) => m + acc)
    //    val maxVelocity = modifiers.getFirst[ModActionParameters]("RecalculateVelocity").parameters.getOrElse("maxVelocity", 50)
    val maxVelocity = modifiers.getFirst[ModRecalculateVelocityParameters].maxVelocity

    //Limiting the speed
    if (velocity.magnitude() > maxVelocity)
      velocity = velocity.unitVector() * maxVelocity

    modifiers.update(modifier.ModVelocity(velocity, "standard"))
    new StepOutput()
  }
}
