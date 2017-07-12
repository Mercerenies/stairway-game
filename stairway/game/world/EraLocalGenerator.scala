
package com.mercerenies.stairway
package game.world

import game.belt.ConveyerFeed
import util.Index
import space.{Space, IceSpace}

trait EraLocalGenerator[+T <: GeneratorFeed] extends Generator[T] {

  abstract override def eraChanged(newEra: Int): Unit = {
    this.localReset()
    this.resetState()
    super.eraChanged(newEra)
  }

  def localReset(): Unit = {}

}
