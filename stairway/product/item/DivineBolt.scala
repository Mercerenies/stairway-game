
package com.mercerenies.stairway.product.item

import com.mercerenies.stairway.product._
import com.mercerenies.stairway.image.ItemsImage
import com.mercerenies.stairway.game.Player
import com.mercerenies.stairway.game.attack.LightningAttack
import java.awt.Image

case object DivineBolt extends Item {

  override def canBeUsed(player: Player): Boolean = player.master.currentEnemy match {
    case None => false
    case Some(x) => !x.isBoss
  }

  override def name: String = "Divine Bolt"

  override def description: String = "Instantly deal 100 damage to non-boss enemy"

  override def index: Int = 9

  override def isPassive: Boolean = false

  override def price(player: Player): Int = 18 // TODO Set the prices accurately

  override def use(player: Player): Unit = {
    player.master.currentEnemy.foreach(_.takeDamage(new LightningAttack(player.master)))
  }

}
