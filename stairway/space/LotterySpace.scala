
package com.mercerenies.stairway.space

import com.mercerenies.stairway.game.StandardGame
import com.mercerenies.stairway.game.content.LotteryContent
import com.mercerenies.stairway.luck.DiceValue

case class LotterySpace(val diceCount: Int, val toBeat: DiceValue, val multiplier: Int) extends ImageSpace {

  override def imageIndex: Int = 6

  override def onLand(master: StandardGame.Master) = {
    master.contentArea.put(new LotteryContent(master.contentArea, this))
  }

  override def onDepart(master: StandardGame.Master) = {
    master.contentArea.clear()
  }

}
