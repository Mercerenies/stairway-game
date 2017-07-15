
package com.mercerenies.stairway.space

import com.mercerenies.stairway.game.StandardGame

case object SpilledMilkSpace extends ImageSpace {

  override def imageIndex: Int = 21

  def damage: Double = 3.0

  override def onLand(master: StandardGame.Master) = {
    master.meter.health.value -= damage
  }

  override def onEmulate(master: StandardGame.Master) = {
    master.meter.health.value -= damage
  }

}
