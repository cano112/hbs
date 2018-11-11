package pl.edu.agh.hbs.model.skill.moving

import pl.edu.agh.hbs.model.Agent
import pl.edu.agh.hbs.model.skill.moving.decision.DecMove

trait MovingAgent extends Agent {
  this.decisions += DecMove
}
