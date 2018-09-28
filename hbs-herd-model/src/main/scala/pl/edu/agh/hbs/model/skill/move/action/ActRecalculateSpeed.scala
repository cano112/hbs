package pl.edu.agh.hbs.model.skill.move.action

import pl.edu.agh.hbs.model.modifier_cardinality.ModifierBuffer
import pl.edu.agh.hbs.model.skill.basic.modifier._
import pl.edu.agh.hbs.model.skill.{Action, Message}

object ActRecalculateSpeed extends Action {

  override def stepsDuration: Int = 1

  override def action(modifiers: ModifierBuffer): Seq[Message] = {
    val position = modifiers.getFirst[ModPosition].position
    val speed = modifiers.getFirst[ModSpeed].speed

    val neighbours = modifiers.getAll[ModVisibleAgent]
    val remainingNeighbours = scala.collection.mutable.ListBuffer.empty[ModVisibleAgent]
    neighbours.copyToBuffer(remainingNeighbours)
    val distance = 50
    val radius = 120
    neighbours.foreach(e => {
      if (e.position.distance(position) > distance) {
        remainingNeighbours - e
      } else {
        val agentAngle =
          if (speed(0) != 0) math.atan(speed(1) / speed(0))
          else if (speed(1) >= 0) 0
          else 180
        val distanceAngle =
          if (e.position(0) - position(0) != 0) math.atan((e.position(1) - position(1)) / (e.position(0) - position(0)))
          else if (e.position(1) - position(1) >= 0) 0
          else 180
        if (math.abs(agentAngle - distanceAngle) > radius)
          remainingNeighbours - e
      }
    })

    var speedX: Double = speed(0)
    var speedY: Double = speed(1)

    if (remainingNeighbours.nonEmpty) {
      val firstConditionWeight = 0.1
      val secondConditionWeight = 0.1
      val thirdConditionWeight = 0.1
      val minimalDistance = 20

      val avgSpeedX = remainingNeighbours.map(e => e.speed(0)).sum / remainingNeighbours.size
      val avgSpeedY = remainingNeighbours.map(e => e.speed(1)).sum / remainingNeighbours.size
      speedX += firstConditionWeight * (avgSpeedX - speed(0))
      speedY += firstConditionWeight * (avgSpeedY - speed(1))

      val avgDistance = remainingNeighbours.map(e => e.position.distance(position)).sum / remainingNeighbours.size
      remainingNeighbours.foreach(e => {
        val distance = e.position.distance(position)
        speedX += secondConditionWeight * (e.position(0) - position(0)) * (distance - avgDistance) / avgDistance
        speedY += secondConditionWeight * (e.position(1) - position(1)) * (distance - avgDistance) / avgDistance
      })

      remainingNeighbours.foreach(e => {
        val distance = e.position.distance(position)
        speedX -= thirdConditionWeight * ((e.position(0) - position(0)) * minimalDistance / distance - (e.position(0) - position(0)))
        speedY -= thirdConditionWeight * ((e.position(1) - position(1)) * minimalDistance / distance - (e.position(1) - position(1)))
      })
    }

    val r = scala.util.Random
    val noiseFactor = 10
    val maxSpeed = 4
    val speedFactor = 0.75
    speedX += noiseFactor * r.nextDouble()
    speedY += noiseFactor * r.nextDouble()
    if (speedX > maxSpeed)
      speedX *= speedFactor
    if (speedY > maxSpeed)
      speedY *= speedFactor

    modifiers.update(ModSpeed(new pl.edu.agh.hbs.model.Vector(speedX.toInt, speedY.toInt)))
    Seq()
  }
}
