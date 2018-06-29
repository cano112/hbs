package pl.edu.agh.hbs.view

case class CircleShape(factor: Int) extends AgentViewShape("circle", Part("c", 0, 0, 5 * factor)) {
}
