
package com.mercerenies.stairway.space

import com.mercerenies.stairway.game.StandardGame

case class RootSpace(damage: Double = 10.0) extends ImageSpace {

  override def imageIndex: Int = 25

  override def onLand(master: StandardGame.Master) = {
    val meter = master.meter
    val taken = math.min(meter.health.value.toDouble, damage)
    meter.health.value -= taken
    master.field.rootEnergy += taken
  }

  override def onEmulate(master: StandardGame.Master) = {
    onLand(master)
  }

}
