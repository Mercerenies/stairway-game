
package com.mercerenies.stairway.product.item

import com.mercerenies.stairway.product._
import com.mercerenies.stairway.image.ItemsImage
import com.mercerenies.stairway.game.Player
import com.mercerenies.stairway.game.attack.FireAttack
import java.awt.Image

case object HolyFire extends Item {

  override def canBeUsed(player: Player): Boolean = player.master.isInCombat

  override def name: String = "Holy Fire"

  override def description: String = "Deal damage equal to ATK, +20 when used on undead"

  override def index: Int = 7

  override def isPassive: Boolean = false

  override def basePrice: Int = 16

  override def use(player: Player): Unit = {
    player.master.currentEnemy.foreach(_.takeDamage(new FireAttack(player.master)))
  }

}
