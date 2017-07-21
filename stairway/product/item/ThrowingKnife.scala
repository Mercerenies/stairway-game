
package com.mercerenies.stairway
package product.item

import product._
import image.ItemsImage
import game.Player
import game.attack.KnifeAttack
import java.awt.Image

case object ThrowingKnife extends Item {

  override def canBeUsed(player: Player): Boolean = player.master.isInCombat

  override def name: String = "Throwing Knife"

  override def description: String = "Instantly deal damage equal to ATK power"

  override def index: Int = 6

  override def isPassive: Boolean = false

  override def basePrice: Int = 9

  override def use(player: Player): Unit = {
    player.master.currentEnemy.foreach(_.takeDamage(new KnifeAttack(player.master)))
  }

}
