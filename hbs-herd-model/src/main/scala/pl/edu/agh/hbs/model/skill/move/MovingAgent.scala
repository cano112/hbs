package pl.edu.agh.hbs.model.skill.move

import pl.edu.agh.hbs.model.Agent
import pl.edu.agh.hbs.model.skill.basic.modifier.ModPosition
import pl.edu.agh.hbs.model.skill.move.action.ActMove
import pl.edu.agh.hbs.model.skill.move.decision.DecMove

trait MovingAgent extends Agent {
  private val r = scala.util.Random

  this.decisions += DecMove
  this.actions += ActMove
  this.modifiers += new ModPosition(r.nextInt(500), r.nextInt(500))
}
