
package com.mercerenies.stairway.space

import com.mercerenies.stairway.game.StandardGame
import com.mercerenies.stairway.game.content.MysteryContent
import com.mercerenies.stairway.luck.DiceValue

case class MysterySpace(val count: Int) extends ImageSpace {

  override def imageIndex: Int = 13

  override def onLand(master: StandardGame.Master) = {
    master.contentArea.put(new MysteryContent(master.contentArea, count))
  }

  override def onDepart(master: StandardGame.Master) = {
    master.contentArea.clear()
  }

}
