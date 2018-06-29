package pl.edu.agh.hbs.view

case class HumanShape(factor: Int) extends AgentViewShape("human", Part("p", -5 * factor, 5 * factor, 5 * factor, 5 * factor, 0, -5 * factor), Part("c", 0, -7 * factor, 4 * factor)) {
}
