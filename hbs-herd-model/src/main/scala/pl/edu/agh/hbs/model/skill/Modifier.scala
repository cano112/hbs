package pl.edu.agh.hbs.model.skill

abstract class Modifier(val label: String = "") extends Serializable {
  def copy(): Modifier
}
