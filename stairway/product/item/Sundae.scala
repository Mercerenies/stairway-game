
package com.mercerenies.stairway.product.item

import com.mercerenies.stairway.product._
import com.mercerenies.stairway.image.ItemsImage
import com.mercerenies.stairway.game.Player
import java.awt.Image

case object Sundae extends Item {

  override def canBeUsed(player: Player): Boolean = true

  override def name: String = "Sundae"

  override def description: String = "Restore half a meter of health immediately"

  override def index: Int = 3

  override def isPassive: Boolean = false

  override def basePrice: Int = 27

  override def use(player: Player): Unit = {
    player.master.meter.health.value += 50
  }

}
