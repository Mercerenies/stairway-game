
package com.mercerenies.stairway.product

import com.mercerenies.stairway.game.Player
import com.mercerenies.stairway.util.Rectangle
import com.mercerenies.stairway.image.FruitsImage
import java.awt._

object Melon extends FruitProduct {

  override def price(player: Player): Int = player.master.stats.melonPrice

  override def giveTo(player: Player): Unit = {
    player.master.buttonPad.melonButton.count += 1
  }

  override def use(player: Player): Unit = {
    val master = player.master
    master.damage.advance(master.stats.melonEffect)
  }

  override def image: Image = Fruits.image.melon

  override def toString(): String = "Melon"

}
