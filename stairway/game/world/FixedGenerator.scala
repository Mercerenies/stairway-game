
package com.mercerenies.stairway.game.world

import com.mercerenies.stairway.game.belt.ConveyerFeed
import com.mercerenies.stairway.util.{Index, RandomImplicits}

trait FixedGenerator[+T <: ConveyerFeed] extends TimedGenerator[T] {

  def fixedTimer: Int

  override def minTimer: Int = fixedTimer

  override def maxTimer: Int = fixedTimer

}
