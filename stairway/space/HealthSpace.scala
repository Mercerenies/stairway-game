
package com.mercerenies.stairway.space

import com.mercerenies.stairway.game.StandardGame

case object HealthSpace extends ImageSpace {

  override def imageIndex: Int = 14

  override def onLand(master: StandardGame.Master) = {
    master.meter.health.value += master.stats.healthSpaceAmount
  }

  override def name = "Health Space"

  override def desc = "Restores a small amount of your health"

}
