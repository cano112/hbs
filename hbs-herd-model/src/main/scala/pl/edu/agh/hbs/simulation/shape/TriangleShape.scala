package pl.edu.agh.hbs.simulation.shape

import pl.edu.agh.hbs.model.representation.elm.{Part, Shape}

case class TriangleShape(factor: Int) extends Shape("triangle-" + factor, Part("p", -5, 5, 5, 5, 0, -5) * factor)
