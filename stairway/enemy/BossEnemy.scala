
package com.mercerenies.stairway.enemy

import com.mercerenies.stairway.game.{Player, StandardGame}
import com.mercerenies.stairway.util.Rectangle
import java.awt.{Graphics2D, Font, Color}

trait BossEnemy extends Enemy {

  private var textShow = 0
  private val textRate = 30 // Show a new character every 30 steps (or 2 per second)

  def bossName: String

  def bossNameDisplay: String =
    bossName.substring(0, textShow / textRate)

  override def isBoss: Boolean = true

  override def onDeath(player: Player): Unit = {
    super.onDeath(player)
    player.master.advanceEra()
  }

  override def step(): Unit = {
    super.step()
    if (bossNameDisplay != bossName)
      textShow += 1
  }

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
    super.draw(graph, rect)

    graph.setColor(Color.black)
    graph.setFont(BossEnemy.DefaultFont)
    val metrics = graph.getFontMetrics()
    val xPos = this.rect.centerX - metrics.stringWidth(bossName) / 2
    val yPos = this.rect.ymin - 4

    graph.drawString(bossNameDisplay, xPos.toFloat, yPos.toFloat)

  }

}

object BossEnemy {

  val DefaultFont = new Font(Font.SANS_SERIF, Font.BOLD, 30)

}
