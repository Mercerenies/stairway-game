
package com.mercerenies.stairway.game.content

import com.mercerenies.stairway.ui.SizedDrawable
import com.mercerenies.stairway.util
import com.mercerenies.stairway.util.Rectangle
import com.mercerenies.stairway.product.Purchasable
import com.mercerenies.stairway.game.Player
import com.mercerenies.stairway.stat.ImprovableStats
import java.awt.{List => _, _}

class DojoContent(contentArea: ContentArea, _stats: Seq[ImprovableStats.UpgradeSlot[_]])
    extends PurchaseContent(contentArea) with ContentHeader {
  import util.GraphicsImplicits._

  class CaptionedUpgradeSlot[T](val slot: ImprovableStats.UpgradeSlot[T])
      extends Purchasable with SizedDrawable {

    override def price(player: Player) = slot.price(player)

    override def giveTo(player: Player) = slot.giveTo(player)

    override def dims: (Double, Double) = DojoContent.SlotDims

    override def draw(graph: Graphics2D, rect: Rectangle): Unit = {

      graph.setColor(Color.white)
      graph.fill(rect)
      graph.setColor(Color.black)
      graph.draw(rect)

      graph.setFont(DojoContent.DefaultFont)
      graph.drawString(
        s"Train ${slot.stat.name} ($$${price(master.player)}) - ${slot.stat.description}",
        rect.xmin + 16,
        rect.ymax - 16
      )

    }

  }

  val stats = _stats.map(new CaptionedUpgradeSlot(_))

  override def headerText: String = "Welcome to the dojo! You may pay for training in different skills here."

  override lazy val items: Seq[PurchaseContentSlot[CaptionedUpgradeSlot[_]]] = {
    val startX = contentArea.rect.xmin
    val startY = contentArea.rect.ymin + 32
    stats.zipWithIndex.map({ case (stat, i) =>
      new PurchaseContentSlot(
        this,
        stat,
        (startX, startY + (DojoContent.SlotDims._2 + 16) * i),
        None
      )
    })
  }

}

object DojoContent {

  val DefaultFont = new Font(Font.SANS_SERIF, Font.PLAIN, 11)

  val SlotDims: (Double, Double) = (300, 40)

}
