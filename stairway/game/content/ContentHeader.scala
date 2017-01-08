
package com.mercerenies.stairway.game.content

import com.mercerenies.stairway.util
import com.mercerenies.stairway.util.Rectangle
import java.awt._

trait ContentHeader {
  import util.GraphicsImplicits._

  def headerText: String

  def drawHeader(graph: Graphics2D, rect: Rectangle): Unit = {
    graph.setFont(ContentHeader.Font)
    graph.setColor(Color.black)
    val metrics = graph.getFontMetrics()
    val text = headerText
    val xPos = rect.centerX - metrics.stringWidth(text) / 2
    graph.drawString(text, xPos, rect.y + 32)
  }

}

object ContentHeader {
  lazy val Font = new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, 12)
}
