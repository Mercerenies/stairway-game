
package com.mercerenies.stairway.space

import com.mercerenies.stairway.game.StandardGame

case object WebSpace extends ImageSpace {

  override def imageIndex: Int = 20

  override def onLand(master: StandardGame.Master) = {
    master.meter.energy.value -= master.meter.energy.value.toDouble
  }

}
