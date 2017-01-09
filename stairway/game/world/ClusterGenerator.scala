
package com.mercerenies.stairway.game.world

import com.mercerenies.stairway.game.belt.ConveyerFeed
import com.mercerenies.stairway.util.Index
import com.mercerenies.stairway.space.{Space, IceSpace}
import scala.util.Random

trait ClusterGenerator[+T <: GeneratorFeed] extends Generator[T] {

  def clusterSize(): Int

  abstract override def trigger(index: Index): Unit = {
    val amount = clusterSize()
    for (i <- 0 until amount)
      super.trigger(index + i)
  }

}
