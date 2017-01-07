
package com.mercerenies.stairway.ui

import javax.swing._
import java.awt._
import com.mercerenies.stairway.game.EventDispatch

class Panel(private val _events: EventDispatch) extends JPanel {

  override def paintComponent(graph: Graphics): Unit = {
    super.paintComponent(graph)
    graph match { // Should always match
      case graph2D: Graphics2D => _events.draw(graph2D)
    }
  }

  setMinimumSize(Panel.DisplayDim)
  setPreferredSize(Panel.DisplayDim)
  setMaximumSize(Panel.DisplayDim)

}

object Panel {
  val DisplayDim = new Dimension(640, 480)
  def width = DisplayDim.getWidth()
  def height = DisplayDim.getHeight()
}
