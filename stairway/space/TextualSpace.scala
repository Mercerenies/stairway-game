
package com.mercerenies.stairway.space

import java.awt.Graphics2D
import com.mercerenies.stairway.util.{Rectangle, GraphicsImplicits}
import com.mercerenies.stairway.game.StandardGame

// This class is mostly for debugging
case class TextualSpace(text: String) extends Space {
  import GraphicsImplicits._

  override def onLand(master: StandardGame.Master) = {}

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
    super.draw(graph, rect)
    graph.drawString(text, rect.centerX, rect.centerY)
  }

  override def name = text

  override def desc = "No special effect"

}
