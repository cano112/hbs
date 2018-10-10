package pl.edu.agh.hbs.model.skill.move.action

import pl.edu.agh.hbs.model
import pl.edu.agh.hbs.model.cardinality.ModifierBuffer
import pl.edu.agh.hbs.model.skill.basic.modifier._
import pl.edu.agh.hbs.model.skill.{Action, Message}

object ActRecalculateVelocity extends Action {

  override def stepsDuration: Int = 0

  override def action(modifiers: ModifierBuffer): Seq[Message] = {
    val position = modifiers.getFirst[ModPosition].position
    val velocity = modifiers.getFirst[ModVelocity].velocity

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
          if (velocity(0) != 0) math.atan(velocity(1) / velocity(0))
          else if (velocity(1) >= 0) 0
          else 180
        val distanceAngle =
          if (e.position(0) - position(0) != 0) math.atan((e.position(1) - position(1)) / (e.position(0) - position(0)))
          else if (e.position(1) - position(1) >= 0) 0
          else 180
        if (math.abs(180 * (agentAngle - distanceAngle) / math.Pi) > radius)
          remainingNeighbours - e
      }
    })

    var velocityX: Double = velocity(0)
    var velocityY: Double = velocity(1)
    val maxSpeed = 4.0

    if (remainingNeighbours.nonEmpty) {
      val neighbourVelocityInfluenceWeight = 0.1
      val needToBeInCenterWeight = 0.1
      val thirdConditionWeight = 0.1
      val minimalDistance = 20

      //adjust speed to neighbours speed (avgSpeed with speed of this element)
      val avgVelocityX = (remainingNeighbours.map(e => e.velocity(0)).sum + velocity(0)) / (remainingNeighbours.size + 1)
      val avgVelocityY = (remainingNeighbours.map(e => e.velocity(1)).sum + velocity(1)) / (remainingNeighbours.size + 1)
      velocityX += neighbourVelocityInfluenceWeight * (avgVelocityX - velocity(0))
      velocityY += neighbourVelocityInfluenceWeight * (avgVelocityY - velocity(1))

      //perturbation
      val r = scala.util.Random
      val noiseWeight = 0.1
      velocityX += noiseWeight * ((r.nextDouble() - 0.5) * maxSpeed)
      velocityY += noiseWeight * ((r.nextDouble() - 0.5) * maxSpeed)

      val avgDistance = remainingNeighbours.map(e => e.position.distance(position)).sum / remainingNeighbours.size
      remainingNeighbours.foreach(e => {
        val distance = e.position.distance(position)
        velocityX += needToBeInCenterWeight * (e.position(0) - position(0)) * (distance - avgDistance) / avgDistance
        velocityY += needToBeInCenterWeight * (e.position(1) - position(1)) * (distance - avgDistance) / avgDistance
      })

      remainingNeighbours.foreach(e => {
        val distance = e.position.distance(position)
        velocityX -= thirdConditionWeight * ((e.position(0) - position(0)) * minimalDistance / distance - (e.position(0) - position(0)))
        velocityY -= thirdConditionWeight * ((e.position(1) - position(1)) * minimalDistance / distance - (e.position(1) - position(1)))
      })
    }

    val speedFactor = 0.75
    if (velocityX > maxSpeed)
      velocityX *= speedFactor
    if (velocityY > maxSpeed)
      velocityY *= speedFactor

    modifiers.update(ModVelocity(model.Vector(velocityX, velocityY)))
    Seq()
  }
}
