package pl.edu.agh.hbs.simulation.species

import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.{Agent, Species, SpeciesObject}
import pl.edu.agh.hbs.simulation.agent.FishAgent

class Fish extends Species

object Fish extends SpeciesObject {
  override val species = new Fish

  override def newAgent(initModifiers: Seq[Modifier]): Agent = new FishAgent(initModifiers)
}
