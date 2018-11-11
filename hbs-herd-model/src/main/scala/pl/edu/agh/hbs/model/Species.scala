package pl.edu.agh.hbs.model

import pl.edu.agh.hbs.model.skill.Modifier

trait Species {
}

trait SpeciesObject {

  val species: Species

  def newAgent(initModifiers: Seq[Modifier]): Agent

  private var counter = 0

  final def nextId(): String = {
    counter += 1
    this.species.getClass.toString + counter
  }

}
