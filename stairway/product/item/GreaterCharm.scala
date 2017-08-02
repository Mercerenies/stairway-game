
package com.mercerenies.stairway
package product.item

import product._
import image.ItemsImage
import game.Player
import space.RedSpace
import java.awt.Image

case object GreaterCharm extends Item with Charm {

  override def canBeUsed(player: Player): Boolean =
    false

  override def name: String = "Greater Talisman"

  override def description: String = "All types of red spaces deal only 1 damage"

  override def index: Int = 15

  override def isPassive: Boolean = true

  override def basePrice: Int = 50 // TODO Set the price

  override def isProtectedAgainst(space: RedSpace.Severity): Boolean =
    true

  override def use(player: Player): Unit = {}

}
