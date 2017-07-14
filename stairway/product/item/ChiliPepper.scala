
package com.mercerenies.stairway
package product.item

import product._
import image.ItemsImage
import game.Player
import status.SpicyEffect
import java.awt.Image

case object ChiliPepper extends Item {

  override def canBeUsed(player: Player): Boolean = true

  override def name: String = "Chili Pepper"

  override def description: String = "Spicy peppers that increase your strength for awhile"

  override def index: Int = 10

  override def isPassive: Boolean = false

  override def price(player: Player): Int = 18 // TODO Set the prices accurately

  override def use(player: Player): Unit = {
    player.afflictStatus(new SpicyEffect)
  }

}
