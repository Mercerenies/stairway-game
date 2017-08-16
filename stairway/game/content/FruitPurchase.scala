
package com.mercerenies.stairway
package game.content

import ui.Drawable
import util.Rectangle
import game.tagline.Tagged
import image.FruitsImage
import product.{FruitProduct, Apple, Orange, Melon, Purchasable, Captioned}
import java.awt.{List => _, _}

class FruitPurchase(contentArea: ContentArea) extends PurchaseContent(contentArea) {

  override lazy val items: Seq[PurchaseContentSlot[FruitProduct]] = {
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

  override def tagged: Option[Tagged] =
    items find { _.isMouseOver } map { _.value }

}
