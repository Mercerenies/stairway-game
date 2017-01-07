
package com.mercerenies.stairway.space

import com.mercerenies.stairway.game.StandardGame
import com.mercerenies.stairway.game.content.ItemPurchase
import com.mercerenies.stairway.product.item.Item

case class ItemSpace(items: Item*) extends ImageSpace {

  override def imageIndex: Int = 7

  override def onLand(master: StandardGame.Master) = {
    master.contentArea.put(new ItemPurchase(master.contentArea, items))
  }

  override def onDepart(master: StandardGame.Master) = {
    master.contentArea.clear()
  }

}
