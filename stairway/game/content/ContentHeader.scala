
package com.mercerenies.stairway.game.content

import com.mercerenies.stairway.util
import com.mercerenies.stairway.util.Rectangle
import java.awt.{List => _, _}

trait ContentHeader {
  import util.GraphicsImplicits._

  def headerText: List[String]

  def drawHeader(graph: Graphics2D, rect: Rectangle): Unit = {
    graph.setFont(ContentHeader.Font)
    graph.setColor(Color.black)
    val metrics = graph.getFontMetrics()
    val header = headerText
    if (!header.isEmpty) {
      val xPos = rect.centerX - header.map { metrics.stringWidth(_) }.max / 2
      var yPos = rect.y + 32
      for (text <- header) {
        graph.drawString(text, xPos, yPos)
        yPos += metrics.getHeight()
      }
    }
  }

}

object ContentHeader {
  lazy val Font = new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, 12)
}
