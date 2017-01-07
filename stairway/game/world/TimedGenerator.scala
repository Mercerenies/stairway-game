
package com.mercerenies.stairway.game.world

import com.mercerenies.stairway.game.belt.ConveyerFeed
import com.mercerenies.stairway.util.{Index, RandomImplicits}

trait TimedGenerator[+T <: ConveyerFeed] extends Generator[T] {
  import RandomImplicits._

  private var timer = computeTimer()

  def minTimer: Int

  def maxTimer: Int

  def computeTimer(): Int = {
    rand.nextInt(minTimer, maxTimer)
  }

  abstract override def forward(next: Index): Unit = {
    timer -= 1
    super.forward(next)
  }

  abstract override def ready: Boolean = (timer <= 0) || (super.ready)

  abstract override def trigger(next: Index): Unit = {
    super.trigger(next)
    timer = computeTimer()
  }

}
