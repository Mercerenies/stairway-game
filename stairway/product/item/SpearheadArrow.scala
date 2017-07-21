
package com.mercerenies.stairway
package product.item

import product._
import image.ItemsImage
import game.Player
import game.attack.ArrowAttack
import java.awt.Image

case object SpearheadArrow extends Item {

  override def canBeUsed(player: Player): Boolean = player.master.isInCombat

  override def name: String = "Spearhead Arrow"

  override def description: String = "Deal damage equal to ATK, doubled on flying enemies"

  override def index: Int = 12

  override def isPassive: Boolean = false

  override def basePrice: Int = 11

  override def use(player: Player): Unit = {
    player.master.currentEnemy.foreach(_.takeDamage(new ArrowAttack(player.master)))
  }

}
