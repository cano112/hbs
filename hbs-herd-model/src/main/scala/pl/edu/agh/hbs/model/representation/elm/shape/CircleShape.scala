package pl.edu.agh.hbs.model.representation.elm.shape

import pl.edu.agh.hbs.model.representation.elm.{Part, Shape}

case class CircleShape(factor: Int) extends Shape("circle-" + factor, Part("c", 0, 0, 5) * factor)
