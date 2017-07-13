
package com.mercerenies.stairway
package game.world

import game.belt.ConveyerFeed
import util.Index
import space.Space
import scala.util.Random

trait SimpleSpaceGenerator[+T <: GeneratorFeed] extends Generator[T] {

  def nextSpace(): Space

  abstract override def trigger(index: Index): Unit = {
    feed.assign(index, nextSpace())
    super.trigger(index + 1)
  }

}
