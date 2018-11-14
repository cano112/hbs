package pl.edu.agh.hbs.simulation.species

import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.model.{Species, SpeciesObject}
import pl.edu.agh.hbs.simulation.agent.WolfAgent

class Wolf extends Species

object Wolf extends SpeciesObject {
  override val species = new Wolf

  override def newAgent(initModifiers: Seq[Modifier]) = new WolfAgent(initModifiers)
}
