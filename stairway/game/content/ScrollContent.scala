
package com.mercerenies.stairway.game.content

import com.mercerenies.stairway.util
import com.mercerenies.stairway.util.Rectangle
import com.mercerenies.stairway.ui.Drawable
import com.mercerenies.stairway.image.FruitsImage
import com.mercerenies.stairway.product.{Scroll}
import com.mercerenies.stairway.action.{MouseClick, MouseButton, ActionType}
import java.awt.{List => _, _}

class ScrollContent(contentArea: ContentArea, scrolls: Seq[Scroll])
    extends AbstractContent(contentArea)
    with ContentHeader {
  import util.PointImplicits._

  private var finished = false

  case class ScrollBox(n: Int, scroll: Scroll) extends Drawable {

    def rect: Rectangle = {
      val total = scrollBoxes.size.toDouble
      val rect = contentArea.rect
      val xPos = util.lerp(rect.xmin, rect.xmax, (n + 1) / (total + 2))
      val yPos = util.lerp(rect.ymin, rect.ymax, 0.5)
      Rectangle(
        xPos - scroll.dims._1 / 2, yPos - scroll.dims._2 / 2,
        xPos + scroll.dims._1 / 2, yPos + scroll.dims._2 / 2
      )
    }

    override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
      if (!scroll.used) {
        scroll.draw(graph, rect)
      }
    }

  }

  val scrollBoxes: Seq[ScrollBox] = scrolls.zipWithIndex.map { case (s, i) => ScrollBox(i, s) }

  override def headerText: List[String] =
    List(
      "Choose a scroll to activate",
      "The others will grow in strength later"
    )

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
    scrollBoxes.foreach(s => s.draw(graph, s.rect))
    super.drawHeader(graph, rect)
  }

  override def click(click: MouseClick): Unit = click match {
    case MouseClick(MouseButton.Left, ActionType.Up, pos) => {
      scrollBoxes.find(pos within _.rect) match {
        case Some(ScrollBox(_, scroll)) if !scroll.used => {
          if (!finished) {
            scroll.click(master)
            finished = true
          }
        }
        case _ => {}
      }
    }
    case _ => {}
  }

}
