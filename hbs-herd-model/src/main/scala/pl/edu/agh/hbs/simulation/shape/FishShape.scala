package pl.edu.agh.hbs.simulation.shape

import pl.edu.agh.hbs.model.representation.elm.{Part, Shape}

case class FishShape(factor: Int) extends Shape("fish-" + factor, Part("p", -3, 10, 3, 10, 0, 5) * factor, Part("e", 0, 0, 3, 6) * factor)
