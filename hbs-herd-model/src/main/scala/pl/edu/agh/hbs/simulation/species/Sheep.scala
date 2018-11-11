package pl.edu.agh.hbs.simulation.species

import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.{Agent, Species, SpeciesObject}
import pl.edu.agh.hbs.simulation.agent.SheepAgent

class Sheep extends Species

object Sheep extends SpeciesObject {
  override val species = new Sheep

  override def newAgent(initModifiers: Seq[Modifier]): Agent = new SheepAgent(initModifiers)
}
