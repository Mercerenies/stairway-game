
package com.mercerenies.stairway
package game.tagline

import java.awt.{Graphics2D, Color, Font, Rectangle}
import util.GraphicsImplicits._
import game.{GameEntity, StandardGame}

class Tagline(master: StandardGame.Master) extends GameEntity[StandardGame.Master](master) {

  private val font = new Font(Font.SERIF, Font.BOLD, 16)

  def highlighted = master.mouseOver

  def string = for { h <- highlighted ; t <- h.tagText } yield t

  override def draw(graph: Graphics2D): Unit = {
    graph.setFont(font)
    val padding = 4
    val metrics = graph.getFontMetrics(font)
    val height = metrics.getHeight() + padding
    val descent = metrics.getMaxDescent()
    string.foreach { s =>
      val lineCount = graph.stringLines(s, master.roomWidth).length
      graph.setColor(Color.gray)
      graph.fill(new Rectangle(0, 0, master.roomWidth, height * lineCount))
      graph.setColor(Color.black)
      graph.drawLine(0, height * lineCount, master.roomWidth, height * lineCount)
      graph.drawStringWidth(
        s,
        padding * 2,
        height - metrics.getMaxDescent() - padding / 2,
        master.roomWidth
      )
    }
  }

}
