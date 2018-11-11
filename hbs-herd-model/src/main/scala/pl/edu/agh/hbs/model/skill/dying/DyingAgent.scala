package pl.edu.agh.hbs.model.skill.dying

import pl.edu.agh.hbs.model.Agent
import pl.edu.agh.hbs.model.skill.dying.decision.DecDie

trait DyingAgent extends Agent {
  this.decisions += DecDie
}
