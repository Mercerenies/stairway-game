
package com.mercerenies.stairway
package game.content

import ui.Drawable
import util.{Rectangle, PointImplicits, GraphicsImplicits}
import image.ItemsImage
import game.Inventory
import game.tagline.Tagged
import product.item.{Item, ItemSlot, TaggedItemSlot}
import java.awt.{List => _, _}

class ItemPurchase(contentArea: ContentArea, private val _items: Seq[Item])
    extends PurchaseContent(contentArea) {
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
    items find { _.isMouseOver } map { _.value.item }
  }

  override def tagged: Option[Tagged] =
    items find { _.isMouseOver } map { slot => TaggedItemSlot(slot.value, master.player) }

}
