package pl.edu.agh.hbs.simulation.shape

import pl.edu.agh.hbs.model.representation.elm.{Part, Shape}

case class SheepShape(factor: Int) extends Shape("sheep-" + factor, Part("c", 0, -4, 3) * factor, Part("e", 0, 0, 2, 4) * factor)
