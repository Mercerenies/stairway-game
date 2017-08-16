
package com.mercerenies.stairway.space

import com.mercerenies.stairway.game.StandardGame
import com.mercerenies.stairway.game.content.FruitPurchase

case object FruitSpace extends ImageSpace {

  override def imageIndex: Int = 9

  override def onLand(master: StandardGame.Master) = {
    master.contentArea.put(new FruitPurchase(master.contentArea))
  }

  override def onDepart(master: StandardGame.Master) = {
    master.contentArea.clear()
  }

  override def name = "Fruit Space"

  override def desc = "You can buy fruits on this space"

}
