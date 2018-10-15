package pl.edu.agh.hbs.model.skill.move

import pl.edu.agh.hbs.model.Agent
import pl.edu.agh.hbs.model.skill.move.decision.DecMove
import pl.edu.agh.hbs.model.skill.move.instantAction.ActRecalculateVelocity2

trait FlockingAgent extends Agent {
  this.beforeStepActions += ActRecalculateVelocity2
  this.decisions += DecMove
}
