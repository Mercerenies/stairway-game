
package com.mercerenies.stairway
package game

import util.Rectangle
import event.{StepEvent, AbstractClickEvent}
import image.ItemsImage
import product.item._
import scala.collection.mutable.ArrayBuffer
import java.awt.{Graphics2D, Color, Font}

class Inventory(master: StandardGame.Master, val xPos: Double, val yPos: Double)
    extends GameEntity[StandardGame.Master](master) {
  import util.PointImplicits._
  import util.GraphicsImplicits._

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

  private var _capacity: Int = 3
  private val contents: ArrayBuffer[Item] = new ArrayBuffer(_capacity)
  private val font: Font = Inventory.DefaultFont

  def isFull: Boolean = contents.size == _capacity
  def isEmpty: Boolean = contents.isEmpty

  def capacity = _capacity
  def capacity_=(cap: Int) = {
    if (cap < 0)
      _capacity = 0
    else
      _capacity = cap
  }

  def positionOf(index: Int): Rectangle = {
    val padding = 40
    val xCurr = xPos + padding * index
    val yCurr = yPos + 28
    Rectangle(xCurr, yCurr, xCurr + ItemsImage.Width, yCurr + ItemsImage.Height)
  }

  def textPos: (Double, Double) =
    (xPos + 2, yPos)

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

  def clear(): Unit = {
    contents.clear()
  }

  def toList: List[Item] =
    contents.toList

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
    _capacity += 1
  }

  override def draw(graph: Graphics2D): Unit = {

    graph.setColor(Color.black)
    for (i <- 0 until capacity) {
      val rect = positionOf(i)
      if (i < contents.size)
        graph.drawImage(contents(i).image, rect)
      else
        graph.drawImage(Item.image.item(0), rect)
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

  val DefaultFont = new Font(Font.SANS_SERIF, Font.PLAIN, 9)

}
