
package com.mercerenies.stairway.game.world

import com.mercerenies.stairway.game.belt.ConveyerFeed
import com.mercerenies.stairway.util.Index
import com.mercerenies.stairway.space.{Space, IceSpace}
import scala.util.Random

trait LeadInGenerator[+T <: GeneratorFeed] extends Generator[T] {

  def leadSpace: Space = IceSpace

  def leadIn(): Int

  abstract override def trigger(index: Index): Unit = {
    val amount = leadIn()
    for (i <- 0 until amount)
      feed.assign(index + i, leadSpace)
    super.trigger(index + amount)
  }

}
