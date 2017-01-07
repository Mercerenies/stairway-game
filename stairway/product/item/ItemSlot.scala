
package com.mercerenies.stairway.product.item

import com.mercerenies.stairway.game.Player
import com.mercerenies.stairway.product._
import java.awt.Image

class ItemSlot(val item: Item) extends ConsumableSlot(item) with Captioned {

  override def dims: (Double, Double) = item.dims

  override def image: Image = if (consumed) Item.image.emptyItem else item.image

}
