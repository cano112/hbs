package pl.edu.agh.hbs.simulation.shape

import pl.edu.agh.hbs.model.representation.elm.{Part, Shape}

case class BoxShape(factor: Int) extends Shape("box-" + factor, Part("r", 0, 0, 5, 5) * factor)
