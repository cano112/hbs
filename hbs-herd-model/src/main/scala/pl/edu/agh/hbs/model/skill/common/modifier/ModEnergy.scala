package pl.edu.agh.hbs.model.skill.common.modifier

import pl.edu.agh.hbs.model.skill.Modifier

case class ModEnergy(energy: Double, override val label: String) extends Modifier(label) {
  override def copy(): Modifier = ModEnergy(energy, label)
}
