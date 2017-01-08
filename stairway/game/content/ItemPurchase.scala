
package com.mercerenies.stairway.game.content

import com.mercerenies.stairway.ui.Drawable
import com.mercerenies.stairway.util
import com.mercerenies.stairway.util.{Rectangle, PointImplicits, GraphicsImplicits}
import com.mercerenies.stairway.image.ItemsImage
import com.mercerenies.stairway.game.Inventory
import com.mercerenies.stairway.product.item.{Item, ItemSlot}
import java.awt.{List => _, _}

class ItemPurchase(contentArea: ContentArea, private val _items: Seq[Item]) extends PurchaseContent(contentArea) {
  import PointImplicits._
  import GraphicsImplicits._

  override val items: Seq[PurchaseContentSlot[ItemSlot]] = {
    val rect = contentArea.rect
    val imgWidth = ItemsImage.Width
    val imgHeight = ItemsImage.Height
    _items.zipWithIndex.map({ case (item, i) =>
      val slot = new ItemSlot(item)
      new PurchaseContentSlot(
        this,
        slot,
        (
          util.lerp(rect.xmin, rect.xmax, (i + 1).toDouble / (_items.size + 2).toDouble),
          util.lerp(rect.ymin, rect.ymax, 1.0 / 3.0)
        )
      )
    }).toList
  }

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {

    super.draw(graph, rect)

    graph.setFont(Inventory.DefaultFont)
    val metrics = graph.getFontMetrics()
    val xText = util.lerp(rect.xmin, rect.xmax, 0.25)
    val yText = util.lerp(rect.ymin, rect.ymax, 0.75)
    graph.drawString(description, xText, yText)

  }

  def description: String = {
    highlightedItem match {
      case None => ""
      case Some(item) => s"${item.name} - ${item.description}"
    }
  }

  def highlightedItem: Option[Item] = {
    val pos = master.state.mousePosition
    items find (pos within _.rect) map (_.value.item)
  }

}
