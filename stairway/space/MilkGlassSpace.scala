
package com.mercerenies.stairway.space

import com.mercerenies.stairway.game.StandardGame

case class MilkGlassSpace(distance: Int) extends ImageSpace {

  override def imageIndex: Int = 22

  override def onLand(master: StandardGame.Master) = {
    master.player nextNPositions distance foreach { pos =>
      master.belt.feed.assignOverride(pos, SpilledMilkSpace)
    }
  }

  override def onEmulate(master: StandardGame.Master) = {
    master.meter.health.value -= RedSpace.Single.damage
  }

  override def name = "Milk Glass Space"

  override def desc = "If you land here, the milk spills and overwrites the next few spaces"

}
