
package com.mercerenies.stairway
package product.item

import product._
import image.ItemsImage
import game.Player
import space.RedSpace
import java.awt.Image

case object LesserCharm extends Item with Charm {

  override def canBeUsed(player: Player): Boolean =
    false

  override def name: String = "Lesser Talisman"

  override def description: String = "Red and Double Red Spaces deal only 1 damage"

  override def index: Int = 14

  override def isPassive: Boolean = true

  override def basePrice: Int = 50

  override def isProtectedAgainst(space: RedSpace.Severity): Boolean = space match {
    case RedSpace.Single | RedSpace.Double => true
    case _ => false
  }

  override def use(player: Player): Unit = {}

}
