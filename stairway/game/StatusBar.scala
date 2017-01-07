
package com.mercerenies.stairway.game

import com.mercerenies.stairway.util
import java.awt.{Graphics2D, Color, Font, Rectangle}

class StatusBar(master: StandardGame.Master) extends GameEntity[StandardGame.Master](master) {

  private val font = new Font(Font.SERIF, Font.BOLD, 16)

  def string = List(
    s"Era ${util.Roman.numeral(master.era)}",
    s"Spaces ${master.spacesMoved}",
    s"ATK ${master.stats.attackPower}",
    s"$$${master.stats.money}",
    s"LUCK ${(master.luck.baseLuck * 100).toInt}%"
  ).mkString(" | ")

  override def draw(graph: Graphics2D): Unit = {
    graph.setFont(font)
    val padding = 4
    val metrics = graph.getFontMetrics(font)
    val height = metrics.getHeight() + padding
    val descent = metrics.getMaxDescent()
    graph.setColor(Color.gray)
    graph.fill(new Rectangle(0, master.roomHeight - height, master.roomWidth, height))
    graph.setColor(Color.black)
    graph.drawLine(0, master.roomHeight - height, master.roomWidth, master.roomHeight - height)
    graph.drawString(
      string,
      padding * 2,
      master.roomHeight - metrics.getMaxDescent() - padding / 2
    )
  }

}
