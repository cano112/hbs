package pl.edu.agh.hbs.simulation.species

import pl.edu.agh.hbs.model.{Agent, SpeciesObject}
import pl.edu.agh.hbs.model.skill.Modifier
import pl.edu.agh.hbs.simulation.agent.WolfAgent

class WolfAlpha extends Wolf

object WolfAlpha extends SpeciesObject {
  val species = new WolfAlpha

  override def newAgent(initModifiers: Seq[Modifier]) = new WolfAgent(initModifiers)
}
