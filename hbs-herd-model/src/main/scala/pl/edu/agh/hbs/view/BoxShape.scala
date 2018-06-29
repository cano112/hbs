package pl.edu.agh.hbs.view

case class BoxShape(factor: Int) extends AgentViewShape("box", Part("r", 0, 0, 5 * factor, 5 * factor)) {
}
