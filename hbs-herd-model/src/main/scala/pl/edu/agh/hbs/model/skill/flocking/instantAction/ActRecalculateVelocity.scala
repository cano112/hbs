//package pl.edu.agh.hbs.model.skill.flocking.instantAction
//
//import pl.edu.agh.hbs.model
//import pl.edu.agh.hbs.model.skill.Action
//import pl.edu.agh.hbs.model.skill.basic.modifier.ModPosition
//import pl.edu.agh.hbs.model.skill.common.modifier
//import pl.edu.agh.hbs.model.skill.common.modifier.{ModNeighbour, ModVelocity}
//import pl.edu.agh.hbs.model.{ModifierBuffer, StepOutput}
//
//object ActRecalculateVelocity extends Action {
//
//  override def action(modifiers: ModifierBuffer): StepOutput = {
//    val position = modifiers.getFirst[ModPosition].position
//    val velocity = modifiers.getFirst[ModVelocity]("standard").velocity
//    val neighbours = modifiers.getAll[ModNeighbour]
//
//    //cohesion / clumping
//    val velocityChange1 = if (neighbours.nonEmpty) {
//      val perceivedCentreOfMass = neighbours.foldLeft(model.Vector())(_ + _.position) / neighbours.size
//      val rule1Factor = 1 / 7.5 //if negative ...
//      (perceivedCentreOfMass - position) * rule1Factor
//    } else model.Vector()
//
//    //separation / avoidance
//    val minimalDistance = 25
//    val filtered = neighbours.filter(n => position.distance(n.position) < minimalDistance)
//    val velocityChange2 = if (filtered.nonEmpty) {
//      val rule2Factor = 1
//      filtered.foldLeft(model.Vector())(_ - _.position + position) * rule2Factor
//    } else model.Vector()
//
//    //alignment / schooling
//    val velocityChange3 = if (neighbours.nonEmpty) {
//      val perceivedVelocity = neighbours.foldLeft(model.Vector())(_ + _.velocity) / neighbours.size
//      val rule3Factor = 0.5
//      (perceivedVelocity - velocity) * rule3Factor
//    } else model.Vector()
//
//    //Action of a strong wind or current
//    //Tendency towards a particular place
//    //Scattering the flock
//    //Tendency away from a particular place
//
//    //Limiting the speed
//    val maxVelocity = 50
//    var newVelocity = velocity + velocityChange1 + velocityChange2 + velocityChange3
//    if (newVelocity.magnitude() > maxVelocity)
//      newVelocity = newVelocity.unitVector() * maxVelocity
//
//    //Bounding the position
//
//    modifiers.update(modifier.ModVelocity(newVelocity, "standard"))
//    new StepOutput()
//  }
//}
