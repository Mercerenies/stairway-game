
package com.mercerenies.stairway.game.button

import com.mercerenies.stairway.game.ButtonPad
import com.mercerenies.stairway.product.FruitProduct
import com.mercerenies.stairway.util
import com.mercerenies.stairway.util.Rectangle
import com.mercerenies.stairway.action.KeyboardKey
import java.awt.{Graphics2D, Font, Color}

abstract class FruitButton(
  pad: ButtonPad,
  initFruits: Int,
  bbox: Rectangle,
  imageIndex: Int,
  hotkeys: Seq[KeyboardKey] = Nil)
    extends Button(pad, bbox, imageIndex, hotkeys) {
  import util.GraphicsImplicits._

  private val font = new Font(Font.SANS_SERIF, Font.BOLD, 16)
  private var _count = initFruits

  def this(pad: ButtonPad, initFruits: Int, index: Int, hotkeys: Seq[KeyboardKey]) =
    this(pad, initFruits, Button.rect(pad.upperLeft, index), index, hotkeys)
  def this(pad: ButtonPad, index: Int, hotkeys: Seq[KeyboardKey]) =
    this(pad, 0, index, hotkeys)
  def this(pad: ButtonPad, index: Int, hotkey: KeyboardKey) =
    this(pad, 0, index, List(hotkey))

  def product: FruitProduct

  def count: Int = _count
  def count_=(n: Int): Unit = {
    if (n < 0)
      _count = 0
    else
      _count = n
  }

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
    super.draw(graph, rect)
    graph.setColor(if (isPressed) Color.white else Color.black)
    graph.setFont(font)
    val metrics = graph.getFontMetrics()
    val str = count.toString
    val padding = 4
    val xPos = rect.xmax - padding - metrics.stringWidth(str)
    val yPos = rect.ymax - padding
    graph.drawString(str, xPos, yPos)
  }

}
