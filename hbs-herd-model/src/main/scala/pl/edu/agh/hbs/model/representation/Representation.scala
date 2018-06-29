package pl.edu.agh.hbs.model.representation

abstract class Representation(val name: String) {

  def generateConfig(): String

  def generateIdentity(): String

}
