
package com.mercerenies.stairway
package product

import game.Player
import game.button.OrangeButton
import util.Rectangle
import image.FruitsImage
import java.awt._

object Orange extends FruitProduct {

  override def price(player: Player): Int = player.master.stats.orangePrice

  override def giveTo(player: Player): Unit = {
    player.master.buttonPad.orangeButton.count += 1
  }

  override def use(player: Player): Unit = {
    val master = player.master
    master.meter.energy.value += master.stats.orangeEffect
  }

  override def image: Image = Fruits.image.orange

  override def toString(): String = "Orange"

  override def tagText = Some(OrangeButton.buttonDesc)

}
