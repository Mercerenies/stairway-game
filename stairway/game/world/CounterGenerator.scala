
package com.mercerenies.stairway.game.world

import com.mercerenies.stairway.game.belt.ConveyerFeed
import com.mercerenies.stairway.util.{Index, RandomImplicits}

trait CounterGenerator[+T <: ConveyerFeed] extends Generator[T] {
  import RandomImplicits._

  private var _counter = 0

  def counter = _counter

  abstract override def trigger(next: Index): Unit = {
    _counter += 1
    super.trigger(next)
  }

}
