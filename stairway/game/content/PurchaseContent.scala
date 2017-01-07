
package com.mercerenies.stairway.game.content

import com.mercerenies.stairway.ui.SizedDrawable
import com.mercerenies.stairway.util.{Rectangle, PointImplicits}
import com.mercerenies.stairway.action.{MouseClick, MouseButton, ActionType}
import com.mercerenies.stairway.product.Purchasable
import java.awt.Graphics2D

abstract class PurchaseContent(contentArea: ContentArea) extends AbstractContent(contentArea) {
  import PointImplicits._

  def items: Seq[PurchaseContentSlot[PurchaseContentSlot.Element]]

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
    items.foreach((item) => item.draw(graph, item.rect))
  }

  override def click(click: MouseClick): Unit = click match {
    case MouseClick(MouseButton.Left, ActionType.Up, pos) => {
      items.foreach((item) => {
        if (pos within item.rect) {
          item.buy()
        }
      })
    }
    case _ => {}
  }

}
