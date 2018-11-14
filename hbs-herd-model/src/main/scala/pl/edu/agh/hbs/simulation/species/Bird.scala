package pl.edu.agh.hbs.simulation.species

import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.{Agent, Species, SpeciesObject}
import pl.edu.agh.hbs.simulation.agent.BirdAgent

class Bird extends Species

object Bird extends SpeciesObject {
  override val species = new Bird

  override def newAgent(initModifiers: Seq[Modifier]): Agent = new BirdAgent(initModifiers)
}
