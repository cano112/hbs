package pl.edu.agh.hbs.simulation.shape

import pl.edu.agh.hbs.model.representation.elm.{Part, Shape}

case class WolfShape(factor: Int) extends Shape("wolf-" + factor, Part("p", -1, -2, 1, -2, 0, -4) * factor, Part("e", 0, 0, 1, 2) * factor)
