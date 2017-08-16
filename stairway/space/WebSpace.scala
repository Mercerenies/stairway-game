
package com.mercerenies.stairway.space

import com.mercerenies.stairway.game.StandardGame

case object WebSpace extends ImageSpace {

  override def imageIndex: Int = 20

  override def onLand(master: StandardGame.Master) = {
    master.meter.energy.value -= 150
  }

  override def name = "Web Space"

  override def desc = "You lose a significant amount of energy if you land on this space"

}
