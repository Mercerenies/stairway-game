
package com.mercerenies.stairway.game.content

import com.mercerenies.stairway.ui.Drawable
import com.mercerenies.stairway.util
import com.mercerenies.stairway.util.Rectangle
import com.mercerenies.stairway.image.FruitsImage
import com.mercerenies.stairway.product.{Apple, Orange, Melon, Purchasable, Captioned}
import java.awt.{List => _, _}

class FruitPurchase(contentArea: ContentArea) extends PurchaseContent(contentArea) {

  override lazy val items: Seq[PurchaseContentSlot[PurchaseContentSlot.Element]] = {
    val rect = contentArea.rect
    val imgWidth = FruitsImage.Width
    val imgHeight = FruitsImage.Height
    val apple = new PurchaseContentSlot(
      this,
      Apple,
      (util.lerp(rect.xmin, rect.xmax, 1.0 / 4.0) - imgWidth / 2, util.lerp(rect.ymin, rect.ymax, 1.0 / 3.0))
    )
    val orange = new PurchaseContentSlot(
      this,
      Orange,
      (util.lerp(rect.xmin, rect.xmax, 2.0 / 4.0) - imgWidth / 2, util.lerp(rect.ymin, rect.ymax, 1.0 / 3.0))
    )
    val melon = new PurchaseContentSlot(
      this,
      Melon,
      (util.lerp(rect.xmin, rect.xmax, 3.0 / 4.0) - imgWidth / 2, util.lerp(rect.ymin, rect.ymax, 1.0 / 3.0))
    )
    List(apple, orange, melon)
  }

}
