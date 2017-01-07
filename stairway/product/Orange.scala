
package com.mercerenies.stairway.product

import com.mercerenies.stairway.game.Player
import com.mercerenies.stairway.util.Rectangle
import com.mercerenies.stairway.image.FruitsImage
import java.awt._

object Orange extends Purchasable with Usable with Captioned {

  override def price(player: Player): Int = player.master.stats.orangePrice

  override def giveTo(player: Player): Unit = {
    player.master.buttonPad.orangeButton.count += 1
  }

  override def use(player: Player): Unit = {
    val master = player.master
    master.meter.energy.value += master.stats.orangeEffect
  }

  override def image: Image = Fruits.image.orange

  override def dims: (Double, Double) = (FruitsImage.Width, FruitsImage.Height)

}
