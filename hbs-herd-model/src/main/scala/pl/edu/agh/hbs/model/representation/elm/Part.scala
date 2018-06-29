package pl.edu.agh.hbs.model.representation.elm

object Part {
  def apply(kind: String, value: Int*): Part = new Part(kind, value: _*)
}

class Part private(val kind: String, val value: Int*) {
  def *(factor: Int): Part = Part(kind, value.map(m => m * factor): _*)
}
