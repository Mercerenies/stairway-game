
package com.mercerenies.stairway.game.world

import com.mercerenies.stairway.game.belt.ConveyerFeed
import com.mercerenies.stairway.util.Index
import scala.util.Random

trait Generator[+T <: ConveyerFeed] {

  def feed: T

  def rand: Random

  def forward(next: Index): Unit

  def ready: Boolean

  def trigger(next: Index): Unit

  def eraChanged(newEra: Int): Unit

}
