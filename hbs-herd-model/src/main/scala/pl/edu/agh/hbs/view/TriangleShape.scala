package pl.edu.agh.hbs.view

case class TriangleShape(factor: Int) extends AgentViewShape("triangle", Part("p", -5 * factor, 5 * factor, 5 * factor, 5 * factor, 0, -5 * factor)) {
}
