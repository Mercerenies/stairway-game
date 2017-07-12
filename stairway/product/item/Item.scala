
package com.mercerenies.stairway
package product.item

import product._
import image.ItemsImage
import game.Player
import util.IOFriendly
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

  def apply(n: Int) = n match {
    case 1 => SmokeBomb
    case 2 => Spikes
    case 3 => Sundae
    case 4 => Coffee
    case 5 => BowlingBall
    case 6 => ThrowingKnife
    case 7 => HolyFire
    case 9 => DivineBolt
    case _ => sys.error("Invalid item index")
  }

  def unapply(item: Item) = Some(item.index)

  implicit object ItemIsIOFriendly extends IOFriendly[Item] {
    override def write(value: Item, file: IOFriendly.Writer): Unit = {
      value match {
        case Item(n) => IOFriendly.write(n, file)
      }
    }
    override def read(file: IOFriendly.Reader): Item = {
      Item(IOFriendly.read[Int](file))
    }
  }

}
