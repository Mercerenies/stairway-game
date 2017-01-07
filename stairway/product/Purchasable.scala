
package com.mercerenies.stairway.product

import com.mercerenies.stairway.game.Player

trait Purchasable {

  def price(player: Player): Int

  def giveTo(player: Player): Unit

  // canBuy does not check for price; it checks for any auxiliary conditions
  def canBuy(player: Player): Boolean = true

  def buy(player: Player): Boolean = {
    if (canBuy(player)) {
      val cost = price(player)
      player.master.stats.buy(cost) {
        this giveTo player
      }
    } else {
      false
    }
  }

}
