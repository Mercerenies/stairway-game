
package com.mercerenies.stairway
package product

import game.Player
import game.button.AppleButton
import util.Rectangle
import image.FruitsImage
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

  override def tagText = Some(AppleButton.buttonDesc)

}
