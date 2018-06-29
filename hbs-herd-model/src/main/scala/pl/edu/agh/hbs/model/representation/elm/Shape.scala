package pl.edu.agh.hbs.model.representation.elm

case class Shape(name: String, private val parts: Part*) {

  def generateConfig() = ""

}
