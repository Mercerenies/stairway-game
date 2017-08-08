
package com.mercerenies.stairway
package space

import game.StandardGame
import status.SpikeEffect

case class SpikeSpace(damage: Double = 2.0, length: Int = 10) extends ImageSpace {

  override def imageIndex: Int = 26

  override def onLand(master: StandardGame.Master) = {
    if (!master.player.hasStatus(_.isInstanceOf[SpikeEffect]))
      master.player.afflictStatus(new SpikeEffect(damage, length))
  }

  override def onEmulate(master: StandardGame.Master) = {
    master.meter.health.value -= damage
  }

}
