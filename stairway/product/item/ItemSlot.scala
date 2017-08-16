
package com.mercerenies.stairway
package product.item

import game.Player
import game.tagline.Tagged
import product._
import java.awt.Image

class ItemSlot(val item: Item) extends ConsumableSlot(item) with Captioned {

  override def dims: (Double, Double) = item.dims

  override def image: Image = if (consumed) Item.image.emptyItem else item.image

}
