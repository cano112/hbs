package pl.edu.agh.hbs.view

object Part {
  def apply(kind: String, value: Int*): Part = new Part(kind, value)
}

private class Part(val kind: String, val value: Seq[Int]) {
}
