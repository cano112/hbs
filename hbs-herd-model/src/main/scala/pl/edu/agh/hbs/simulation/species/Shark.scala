package pl.edu.agh.hbs.simulation.species

import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.{Agent, Species, SpeciesObject}
import pl.edu.agh.hbs.simulation.agent.SharkAgent

class Shark extends Species

object Shark extends SpeciesObject {
  override val species = new Shark

  override def newAgent(initModifiers: Seq[Modifier]): Agent = new SharkAgent(initModifiers)
}
