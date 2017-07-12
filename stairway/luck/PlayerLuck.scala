
package com.mercerenies.stairway.luck

import com.mercerenies.stairway.game.StandardGame
import com.mercerenies.stairway.util
import scala.util.Random

class PlayerLuck(val master: StandardGame.Master) {

  private val rand = new Random
  private var _karma: Double = 0.0

  def baseLuck: Double = master.stats.levels.luck.value

  def karma: Double = _karma
  def karma_=(x: Double): Unit = {
    _karma = util.clamp(x, -1.0, 1.0)
  }
  def multiplier: Double = master.stats.levels.chaos.value

  def luck: Double = math.max(baseLuck + karma * multiplier, 0.0)

  def evaluateLuck(weight: (Double, Double), odds: Double): Boolean = {
    // If an event was already determined conclusively without random chance,
    // go with that result without factoring in luck or karma.
    if (odds >= 1.00) {
      true
    } else if (odds <= 0.00) {
      false
    } else {
      // This also adjusts the karma from the event
      // Both weight arguments should be nonnegative
      val winner = (rand.nextDouble() <= odds * luck)
      if (winner)
        karma -= weight._1
      else
        karma += weight._2
      winner
    }
  }

  def evaluateLuck(weight: Double, odds: Double): Boolean = {
    // This also adjusts the karma from the event
    evaluateLuck((weight, weight), odds)
  }

}
