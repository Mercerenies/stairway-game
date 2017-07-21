
package com.mercerenies.stairway
package product.item

import product._
import image.ItemsImage
import game.Player
import status.WingedEffect
import java.awt.Image

case object Anchor extends Item {

  override def canBeUsed(player: Player): Boolean = player.master.currentEnemy match {
    case None => false
    case Some(x) => x.hasStatus(_.isInstanceOf[WingedEffect])
  }

  override def name: String = "Anchor"

  override def description: String = "All flying enemies are grounded"

  override def index: Int = 13

  override def isPassive: Boolean = false

  override def basePrice: Int = 16

  override def use(player: Player): Unit = {
    player.master.currentEnemy.foreach(_.cureStatus(_.isInstanceOf[WingedEffect]))
  }

}
