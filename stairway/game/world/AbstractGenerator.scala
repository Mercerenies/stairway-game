
package com.mercerenies.stairway.game.world

import com.mercerenies.stairway.game.belt.ConveyerFeed
import com.mercerenies.stairway.util.Index
import scala.util.Random

abstract class AbstractGenerator[+T <: ConveyerFeed](
  override val feed: T,
  override val rand: Random)
    extends Generator[T] {

  override def forward(next: Index): Unit = {}

  override def ready: Boolean = false

  override def trigger(next: Index): Unit = {}

  override def eraChanged(newEra: Int): Unit = {}

  override def resetState(): Unit = {}

}
