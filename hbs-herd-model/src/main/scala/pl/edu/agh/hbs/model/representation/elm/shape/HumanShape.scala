package pl.edu.agh.hbs.model.representation.elm.shape

import pl.edu.agh.hbs.model.representation.elm.{Part, Shape}

case class HumanShape(factor: Int) extends Shape("human-" + factor, Part("p", -5, 5, 5, 5, 0, -5) * factor, Part("c", 0, -7, 4) * factor)
