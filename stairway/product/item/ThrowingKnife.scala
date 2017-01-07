
package com.mercerenies.stairway.product.item

import com.mercerenies.stairway.product._
import com.mercerenies.stairway.image.ItemsImage
import com.mercerenies.stairway.game.Player
import com.mercerenies.stairway.game.attack.KnifeAttack
import java.awt.Image

case object ThrowingKnife extends Item {

  override def canBeUsed(player: Player): Boolean = player.master.isInCombat

  override def name: String = "Throwing Knife"

  override def description: String = "Instantly deal damage equal to ATK power"

  override def index: Int = 6

  override def isPassive: Boolean = false

  override def price(player: Player): Int = 18 // TODO Set the prices accurately

  override def use(player: Player): Unit = {
    player.master.currentEnemy.foreach(_.takeDamage(new KnifeAttack(player.master)))
  }

}
