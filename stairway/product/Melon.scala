
package com.mercerenies.stairway
package product

import game.Player
import game.button.MelonButton
import util.Rectangle
import image.FruitsImage
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

  override def tagText: Option[String] = Some(MelonButton.buttonDesc)

}
