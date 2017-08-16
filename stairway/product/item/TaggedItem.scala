
package com.mercerenies.stairway
package product.item

import game.tagline.Tagged
import game.Player

case class TaggedItem(item: Item, player: Player) extends Tagged {

  override def tagText: Option[String] =
    Some(item.fullDescription(player).mkString("\n"))

}
