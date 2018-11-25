package pl.edu.agh.hbs.model.skill.predator.modifier

import pl.edu.agh.hbs.model.skill.Modifier

case class ModFollowPreyParameters(followFactor: Double) extends Modifier {
  override def copy(): Modifier = ModFollowPreyParameters(followFactor)
}
