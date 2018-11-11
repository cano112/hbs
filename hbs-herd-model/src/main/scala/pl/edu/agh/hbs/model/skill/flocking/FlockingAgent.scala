package pl.edu.agh.hbs.model.skill.flocking

import pl.edu.agh.hbs.model.Agent
import pl.edu.agh.hbs.model.skill.flocking.instantAction.ActRecalculateVelocity

trait FlockingAgent extends Agent {
  this.beforeStepActions += ActRecalculateVelocity
}
