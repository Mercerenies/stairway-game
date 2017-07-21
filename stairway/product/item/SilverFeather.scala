
package com.mercerenies.stairway
package product.item

import product._
import image.ItemsImage
import game.Player
import status.WingedEffect
import java.awt.Image

case object SilverFeather extends Item {

  override def canBeUsed(player: Player): Boolean =
    !player.hasStatus(_.isInstanceOf[WingedEffect])

  override def name: String = "Silver Feather"

  override def description: String = "Allows you to fly for a short period of time"

  override def index: Int = 11

  override def isPassive: Boolean = false

  override def basePrice: Int = 23

  override def use(player: Player): Unit = {
    player.afflictStatus(new WingedEffect)
  }

}
