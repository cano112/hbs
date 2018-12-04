package pl.edu.agh.hbs.simulation.shape

import pl.edu.agh.hbs.model.representation.elm.{Part, Shape}

case class EllipseShape(factor: Int) extends Shape("ellipse-" + factor, Part("e", 0, 0, 5, 10) * factor)
