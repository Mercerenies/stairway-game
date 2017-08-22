
package com.mercerenies.stairway
package product.item

import product._
import image.ItemsImage
import game.Player
import status.WingedEffect
import java.awt.Image

case object FourLeafClover extends Item {

  override def canBeUsed(player: Player): Boolean = true

  override def name: String = "Four-Leaf Clover"

  override def description: String = "Increases karma instantly"

  override def index: Int = 16

  override def isPassive: Boolean = false

  override def basePrice: Int = 16 // TODO Price

  override def use(player: Player): Unit = {
    player.master.luck.karma += 0.20
  }

}
