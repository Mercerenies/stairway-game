
package com.mercerenies.stairway.product.item

import com.mercerenies.stairway.product._
import com.mercerenies.stairway.image.ItemsImage
import com.mercerenies.stairway.game.Player
import java.awt.Image

case object SmokeBomb extends Item {

  override def canBeUsed(player: Player): Boolean = player.master.currentEnemy match {
    case None => false
    case Some(x) => !x.isBoss
  }

  override def name: String = "Smoke Bomb"

  override def description: String = "Escape from a non-boss fight"

  override def index: Int = 1

  override def isPassive: Boolean = false

  override def price(player: Player): Int = 18 // TODO Set the prices accurately

  override def use(player: Player): Unit = {
    player.master.currentEnemyBox.foreach(_.instantKill())
  }

}
