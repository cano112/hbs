package pl.edu.agh.hbs.model

import pl.edu.agh.hbs.api.ui.Representation
import pl.edu.agh.hbs.api.ui.dto.Color
import pl.edu.agh.hbs.model.skill.basic.modifier._
import pl.edu.agh.hbs.model.skill.common.instantAction.ActIncrementTimers
import pl.edu.agh.hbs.model.skill.common.modifier.{ModTimer, ModVelocity}
import pl.edu.agh.hbs.model.skill.{Action, Decision, Message, Modifier}

import scala.collection.mutable.ListBuffer

abstract class Agent(private val initModifiers: Seq[Modifier], private val inheritedModifiers: ModifierBuffer)
  extends Serializable {

  val modifiers: ModifierBuffer = new ModifierBuffer()
  protected val decisions: ListBuffer[Decision] = scala.collection.mutable.ListBuffer.empty[Decision]
  protected val beforeStepActions: ListBuffer[Action] = scala.collection.mutable.ListBuffer.empty[Action]
  protected val afterStepActions: ListBuffer[Action] = scala.collection.mutable.ListBuffer.empty[Action]

  private val currentActions: CurrentActions = new CurrentActions
  private val stepOutput: StepOutput = new StepOutput()
  modifiers.update(modifiersCopiedForChild(inheritedModifiers) ++ initModifiers :+ ModLifeStatus())
  afterStepActions += ActIncrementTimers

  def beforeStep(messages: Seq[Message]): Unit = {
    messages.foreach(m => m.process(this))
    beforeStepActions.foreach(a => stepOutput += a.action(modifiers))
  }

  def step(): Unit = {
    if (currentActions.shouldDecide) {
      val currentDecision = decide()
      currentActions.updateQueue(currentDecision)
    }
    stepOutput += currentActions.step(modifiers)
  }

  def afterStep(): StepOutput = {
    afterStepActions.foreach(a => stepOutput += a.action(modifiers))
    stepOutput.clearReturn()
  }

  private def decide(): Decision = {
    var number = -1
    var priority = -1
    for ((decision, i) <- decisions.view.zipWithIndex) {
      if (decision.decision(modifiers) && decision.priority > priority) {
        number = i
        priority = decision.priority
      }
    }
    decisions(number)
  }

  def modifiersCopiedForChild(modifiers: ModifierBuffer): Seq[Modifier] = {
    val initModifiers = ListBuffer.empty[Modifier]
    initModifiers += ModVelocity(Vector(), "standard")
    modifiers.getAll[ModTimer].foreach(m => initModifiers += ModTimer(0, m.label))
    modifiers.getAll[ModPosition].foreach(m => initModifiers += m.copy())
    modifiers.getAll[ModRepresentation].foreach(m => initModifiers += m.copy())
    initModifiers
  }

  def position(): Vector = modifiers.getFirst[ModPosition].position

  def rotation(): Double = {
    val velocity = modifiers.getAll[ModVelocity].map(m => m.velocity).reduce((m, acc) => m + acc)
    val angle =
      if (velocity(0) != 0) math.atan(velocity(1) / velocity(0)) * 180 / math.Pi
      else if (velocity(1) >= 0) 0
      else 180
    if (velocity(0) > 0)
      angle + 90
    else
      angle - 90
  }

  def representation(): Representation = modifiers.getFirst[ModRepresentation].representation

  def color(): Color = {
    modifiers.getAll[ModColor]
      .map(mod => mod.color)
      .headOption
      .getOrElse(Color.ORANGE)
  }

  def id(): String = modifiers.getFirst[ModIdentifier].id


  def canEqual(other: Any): Boolean = other.isInstanceOf[Agent]

  override def equals(other: Any): Boolean = other match {
    case that: Agent =>
      (that canEqual this) &&
        modifiers.getFirst[ModIdentifier].id == that.modifiers.getFirst[ModIdentifier].id
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(modifiers.getFirst[ModIdentifier].id)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
