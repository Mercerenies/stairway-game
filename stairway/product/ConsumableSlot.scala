
package com.mercerenies.stairway.product

import com.mercerenies.stairway.game.Player

class ConsumableSlot(val instance: Purchasable) extends Consumable {

  override def price(player: Player): Int = instance.price(player)

  override def canBuy(player: Player): Boolean = instance.canBuy(player) && super.canBuy(player)

  override def giveTo(player: Player): Unit =
    instance giveTo player

}
