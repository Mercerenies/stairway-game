
package com.mercerenies.stairway.game.content

import com.mercerenies.stairway.ui.{Drawable, SizedDrawable}
import com.mercerenies.stairway.util.{Rectangle, GraphicsImplicits}
import com.mercerenies.stairway.product.{Purchasable, Captioned}
import java.awt._

class PurchaseContentSlot[+T <: PurchaseContentSlot.Element](
  val owner: PurchaseContent,
  val value: T,
  xy: (Double, Double),
  val font: Option[Font] = Some(PurchaseContentSlot.DefaultFont))
    extends Drawable {
  import GraphicsImplicits._

  val xPos = xy._1
  val yPos = xy._2

  def rect: Rectangle = {
    val dims = value.dims
    Rectangle(xPos, yPos, xPos + dims._1, yPos + dims._2)
  }

  def buy(): Boolean = {
    value.buy(owner.master.player)
  }

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
    value.draw(graph, rect)
    font match {
      case None => {}
      case Some(font) => {
        graph.setFont(font)
        val text = if (value.canBuy(owner.master.player)) "$" + value.price(owner.master.player) else "N/A"
        val metrics = graph.getFontMetrics()
        val xPos = rect.centerX - metrics.stringWidth(text) / 2
        val yPos = rect.ymax + metrics.getHeight
        graph.drawString(text, xPos, yPos)
      }
    }
  }

}

object PurchaseContentSlot {

  type Element = Purchasable with SizedDrawable

  lazy val DefaultFont = new Font(Font.SANS_SERIF, Font.PLAIN, 20)

}
