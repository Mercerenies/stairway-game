
package com.mercerenies.stairway.game

import com.mercerenies.stairway.util
import com.mercerenies.stairway.util.Rectangle
import com.mercerenies.stairway.event.{StepEvent, AbstractClickEvent}
import com.mercerenies.stairway.image.ItemsImage
import com.mercerenies.stairway.product.item._
import scala.collection.mutable.ArrayBuffer
import java.awt.{Graphics2D, Color, Font}

class Inventory(master: StandardGame.Master, val xPos: Double, val yPos: Double)
    extends GameEntity[StandardGame.Master](master) {
  import util.PointImplicits._

  private object UseItem extends AbstractClickEvent {

    override def leftMouseUp(x: Int, y: Int): Unit = {
      (highlightedPos, highlightedItem) match {
        case (Some(pos), Some(item)) if item.canBeUsed(master.player) => {
          item.use(master.player)
          removeAt(pos)
        }
        case _ => {}
      }
    }

    override def rightMouseUp(x: Int, y: Int): Unit = {
      highlightedPos match {
        case None => {}
        case Some(pos) => removeAt(pos)
      }
    }

  }

  private var capacity: Int = 3
  private val contents: ArrayBuffer[Item] = new ArrayBuffer(capacity)
  private val font: Font = Inventory.DefaultFont

  def isFull: Boolean = contents.size == capacity
  def isEmpty: Boolean = contents.isEmpty

  def positionOf(index: Int): Rectangle = {
    val padding = 40
    val xCurr = xPos + padding * index
    val yCurr = yPos + 28
    Rectangle(xCurr, yCurr, xCurr + ItemsImage.Width, yCurr + ItemsImage.Height)
  }

  def textPos: (Int, Int) =
    (xPos.toInt + 2, yPos.toInt + 11)

  def getItem(index: Int): Option[Item] =
    if (index < 0 || index >= contents.size)
      None
    else
      Some(contents(index))

  def addItem(item: Item): Unit = {
    if (!isFull)
      contents += item
  }

  def removeItem(item: Item): Unit = {
    contents -= item
  }

  def removeAt(index: Int): Unit = {
    contents remove index
  }

  def highlightedPos: Option[Int] = {
    val pos = master.state.mousePosition
    (0 until capacity).find(pos within positionOf(_))
  }

  def highlightedItem: Option[Item] = {
    for (
      x <- highlightedPos;
      item <- getItem(x)
    ) yield item
  }

  def description: Seq[String] = {
    highlightedItem match {
      case None => Nil
      case Some(item) => item.fullDescription(master.player)
    }
  }

  def increaseCapacity(): Unit = {
    capacity += 1
  }

  override def draw(graph: Graphics2D): Unit = {

    graph.setColor(Color.black)
    for (i <- 0 until capacity) {
      val rect = positionOf(i)
      if (i < contents.size) {
        graph.drawImage(
          contents(i).image,
          rect.x.toInt,
          rect.y.toInt,
          rect.width.toInt,
          rect.height.toInt,
          null
        )
      } else {
        graph.drawImage(
          Item.image.item(0),
          rect.x.toInt,
          rect.y.toInt,
          rect.width.toInt,
          rect.height.toInt,
          null
        )
      }
    }

    graph.setFont(font)
    val metrics = graph.getFontMetrics()
    val (xText, yText) = textPos
    var yCurr = yText
    for (str <- description) {
      graph.drawString(str, xText, yCurr)
      yCurr += metrics.getHeight()
    }

  }

  clickEvent += UseItem

}

object Inventory {

  val DefaultFont = new Font(Font.SANS_SERIF, Font.PLAIN, 11)

}
