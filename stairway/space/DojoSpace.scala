
package com.mercerenies.stairway.space

import com.mercerenies.stairway.game.StandardGame
import com.mercerenies.stairway.game.content.DojoContent
import com.mercerenies.stairway.stat.ImprovableStats

case class DojoSpace(stats: ImprovableStats.UpgradeSlot[_]*) extends ImageSpace {

  override def imageIndex: Int = 17

  override def isOverridable: Boolean = false

  override def onLand(master: StandardGame.Master) = {
    master.contentArea.put(new DojoContent(master.contentArea, stats))
  }

  override def onDepart(master: StandardGame.Master) = {
    master.contentArea.clear()
  }

  override def name = "Dojo Space"

  override def desc = "You can pay money to train your skills and level up here [Protected]"

}
