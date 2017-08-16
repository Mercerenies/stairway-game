
package com.mercerenies.stairway
package product.item

import game.tagline.Tagged
import game.Player

case class TaggedItemSlot(slot: ItemSlot, player: Player) extends Tagged {

  override def tagText: Option[String] =
    if (slot.consumed)
      None
    else
      Some(s"${slot.item.name} ($$${slot.item.price(player)})\n${slot.item.description}")

}
