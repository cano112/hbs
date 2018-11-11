package pl.edu.agh.hbs.model

import pl.edu.agh.hbs.model.skill.Message

import scala.collection.mutable.ListBuffer


class StepOutput(val messages: ListBuffer[Message] = ListBuffer.empty,
                 val agents: ListBuffer[Agent] = ListBuffer.empty,
                 var isDead: Boolean = false) {

  def +(other: StepOutput): StepOutput =
    new StepOutput(this.messages ++ other.messages, this.agents ++ other.agents, this.isDead || other.isDead)

  def +=(other: StepOutput): Unit = {
    this.messages ++= other.messages
    this.agents ++= other.agents
    this.isDead ||= other.isDead
  }

  def clear(): Unit = {
    this.messages.clear()
    this.agents.clear()
    this.isDead = false
  }

  def copy(): StepOutput = new StepOutput(this.messages.clone(), this.agents.clone(), this.isDead)

  def clearReturn(): StepOutput = {
    val result = this.copy()
    this.clear()
    result
  }

}
