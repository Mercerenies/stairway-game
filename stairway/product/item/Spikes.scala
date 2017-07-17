
package com.mercerenies.stairway.product.item

import com.mercerenies.stairway.product._
import com.mercerenies.stairway.image.ItemsImage
import com.mercerenies.stairway.game.Player
import com.mercerenies.stairway.status.SpikeEffect
import java.awt.Image

case object Spikes extends Item {

  override def canBeUsed(player: Player): Boolean = player.master.currentEnemy match {
    case None => false
    case Some(x) => !x.hasStatus(_.isInstanceOf[SpikeEffect])
  }

  override def name: String = "Spikes"

  override def description: String = "Drop spikes, inflicting damage to the enemy each turn"

  override def index: Int = 2

  override def isPassive: Boolean = false

  override def basePrice: Int = 20

  override def use(player: Player): Unit = {
    val damage = spikeDamage(player)
    player.master.currentEnemy.foreach(_.afflictStatus(new SpikeEffect(damage)))
  }

  def spikeDamage(player: Player): Double = math.min(player.master.stats.attackPower / 2.0, 10.0)

}
