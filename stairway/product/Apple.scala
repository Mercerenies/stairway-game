
package com.mercerenies.stairway.product

import com.mercerenies.stairway.game.Player
import com.mercerenies.stairway.util.Rectangle
import com.mercerenies.stairway.image.FruitsImage
import java.awt._

object Apple extends FruitProduct {

  override def price(player: Player): Int = player.master.stats.applePrice

  override def giveTo(player: Player): Unit = {
    player.master.buttonPad.appleButton.count += 1
  }

  override def use(player: Player): Unit = {
    val master = player.master
    master.meter.health.value += master.stats.appleEffect
  }

  override def image: Image = Fruits.image.apple

  override def toString(): String = "Apple"

}
