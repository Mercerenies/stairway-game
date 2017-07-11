
package com.mercerenies.stairway.product.item

import com.mercerenies.stairway.product._
import com.mercerenies.stairway.image.ItemsImage
import com.mercerenies.stairway.game.Player
import java.awt.Image

abstract class Item extends Purchasable with Usable with Captioned {

  def index: Int
  def isPassive: Boolean
  def canBeUsed(player: Player): Boolean
  def name: String
  def description: String

  def fullDescription(player: Player): Seq[String] = {
    val lmb = if (isPassive) "" else if (canBeUsed(player)) "LMB to use, " else "can't be used now, "
    val rmb = "RMB to discard"
    List(name, description, s"($lmb$rmb)")
  }

  override def giveTo(player: Player): Unit =
    player.master.inventory addItem this

  override def canBuy(player: Player): Boolean =
    !player.master.inventory.isFull && super.canBuy(player)

  override def dims: (Double, Double) =
    (ItemsImage.Width, ItemsImage.Height)

  override def image: Image =
      Item.image.item(index)

}

object Item {

  val image = new ItemsImage

}
