
package com.mercerenies.stairway.product

import com.mercerenies.stairway.game.Player

trait Consumable extends Purchasable {

  private var _consumed: Boolean = false

  def consumed: Boolean = _consumed

  override def canBuy(player: Player): Boolean = (!consumed) && super.canBuy(player)

  override def buy(player: Player): Boolean = {
    val result = super.buy(player)
    if (result)
      _consumed = true
    result
  }

}
