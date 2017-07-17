
package com.mercerenies.stairway.product.item

import com.mercerenies.stairway.product._
import com.mercerenies.stairway.image.ItemsImage
import com.mercerenies.stairway.game.Player
import java.awt.Image

case object Coffee extends Item {

  override def canBeUsed(player: Player): Boolean = true

  override def name: String = "Coffee"

  override def description: String = "Restore a full meter of energy immediately"

  override def index: Int = 4

  override def isPassive: Boolean = false

  override def basePrice: Int = 18

  override def use(player: Player): Unit = {
    player.master.meter.energy.value += 100
  }

}
