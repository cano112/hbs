package pl.edu.agh.hbs.simulation.species

import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.{Agent, Species, SpeciesObject}
import pl.edu.agh.hbs.simulation.agent.HumanAgent

class Human extends Species

object Human extends SpeciesObject {
  override val species = new Human

  override def newAgent(initModifiers: Seq[Modifier]): Agent = new HumanAgent(initModifiers)
}
