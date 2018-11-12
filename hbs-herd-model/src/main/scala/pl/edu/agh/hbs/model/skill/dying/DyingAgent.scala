package pl.edu.agh.hbs.model.skill.dying

import pl.edu.agh.hbs.model.Agent
import pl.edu.agh.hbs.model.skill.dying.decision.{DecDie, DecIsDead}
import pl.edu.agh.hbs.model.skill.dying.instantAction.ActConsumeEnergy

trait DyingAgent extends Agent {
  this.decisions += DecDie
  this.decisions += DecIsDead
  this.afterStepActions += ActConsumeEnergy
}
