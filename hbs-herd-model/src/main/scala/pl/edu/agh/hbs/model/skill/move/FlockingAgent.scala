package pl.edu.agh.hbs.model.skill.move

import pl.edu.agh.hbs.model.Agent
import pl.edu.agh.hbs.model.skill.move.decision.DecMove

trait FlockingAgent extends Agent {
  this.decisions += DecMove
}
