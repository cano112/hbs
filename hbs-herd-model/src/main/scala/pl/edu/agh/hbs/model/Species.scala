package pl.edu.agh.hbs.model

import pl.edu.agh.hbs.model.skill.Modifier

trait Species extends Serializable

trait SpeciesObject extends Serializable {

  val species: Species

  def newAgent(initModifiers: Seq[Modifier], inheritedModifiers: ModifierBuffer): Agent

  private var counter = 0

  final def nextId(): String = {
    counter += 1
    this.species.getClass.toString + counter
  }

}
