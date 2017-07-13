
package com.mercerenies.stairway.game.content

import com.mercerenies.stairway.util
import com.mercerenies.stairway.util.Rectangle
import com.mercerenies.stairway.ui.Drawable
import com.mercerenies.stairway.image.FruitsImage
import com.mercerenies.stairway.product.{Fruits, Captioned}
import com.mercerenies.stairway.action.{MouseClick, MouseButton, ActionType}
import java.awt.{List => _, _}

class MysteryContent(contentArea: ContentArea, val count: Int) extends AbstractContent(contentArea) {
  import util.PointImplicits._
  import util.RandomImplicits._
  import util.GraphicsImplicits._

  class MysteryBox(val xPos: Double, val yPos: Double) extends Drawable {

    private var display: Option[Captioned] = None
    private var empty: Boolean = false

    def rect = Rectangle(xPos, yPos, xPos + FruitsImage.Width, yPos + FruitsImage.Height)

    override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
      if (!empty) {
        graph.drawImage(
          display match {
            case None => Fruits.image.mystery
            case Some(x) => x.image
          },
          rect
        )
      }
    }

    def openBox(contents: Option[Captioned]) = contents match {
      case None => {
        empty = true
      }
      case Some(inner) => {
        empty = false
        display = Some(inner)
      }
    }

  }

  private var finished = false
  private var timer = 60

  val boxes: List[MysteryBox] = {
    val xPos = contentArea.rect.x + 10
    val yPos = contentArea.rect.y + 20
    val perRow = 4
    val diff = math.max(FruitsImage.Width, FruitsImage.Height) + 8
    for (i <- (0 until count).toList)
      yield new MysteryBox(xPos + diff * (i % perRow), yPos + diff * (i / perRow))
  }

  override def isIdle: Boolean = !finished || (timer <= 0)

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
    boxes.foreach((box) => box.draw(graph, box.rect))
  }

  override def step(): Unit = {
    if (finished) {
      if (timer > 0) {
        timer -= 1
      } else {
        contentArea.clear()
      }
    }
  }

  override def click(click: MouseClick): Unit = click match {
    case MouseClick(MouseButton.Left, ActionType.Up, pos) => {
      boxes.find(pos within _.rect) match {
        case None => {}
        case Some(box) => {
          if (!finished) {
            val success = master.luck.evaluateLuck(MysteryContent.LuckWeight, 3.0 / count)
            if (success) {
              val result = util.rand.nextOf(Fruits.apple, Fruits.orange, Fruits.melon)
              result giveTo master.player
              box.openBox(Some(result))
            } else {
              box.openBox(None)
            }
            finished = true
          }
        }
      }
    }
    case _ => {}
  }

}

object MysteryContent {
  val LuckWeight = 0.075
}
