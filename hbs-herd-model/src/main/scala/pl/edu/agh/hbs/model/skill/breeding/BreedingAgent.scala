package pl.edu.agh.hbs.model.skill.breeding

import pl.edu.agh.hbs.model.Agent
import pl.edu.agh.hbs.model.skill.breeding.decision.DecBreed

trait BreedingAgent extends Agent {
  this.decisions += DecBreed
}
