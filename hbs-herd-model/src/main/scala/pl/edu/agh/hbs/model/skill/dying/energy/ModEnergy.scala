package pl.edu.agh.hbs.model.skill.dying.energy

import pl.edu.agh.hbs.model.skill.Modifier

case class ModEnergy(energy: Double) extends Modifier {
  override def copy(): Modifier = ModEnergy(energy)
}
