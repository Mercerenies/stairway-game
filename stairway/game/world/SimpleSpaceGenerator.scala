
package com.mercerenies.stairway.game.world

import com.mercerenies.stairway.game.belt.ConveyerFeed
import com.mercerenies.stairway.util.Index
import com.mercerenies.stairway.space.Space
import scala.util.Random

trait SimpleSpaceGenerator[+T <: GeneratorFeed] extends Generator[T] {

  def nextSpace(): Space

  abstract override def trigger(index: Index): Unit = {
    feed.assign(index, nextSpace())
    super.trigger(index)
  }

}
