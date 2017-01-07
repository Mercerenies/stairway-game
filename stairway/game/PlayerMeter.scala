
package com.mercerenies.stairway.game

import java.awt.{Color, Graphics2D}
import com.mercerenies.stairway.stat.{BarStat, StatDrawParams}
import com.mercerenies.stairway.util.Rectangle
import com.mercerenies.stairway.event.AbstractStepEvent

class PlayerMeter(
  master: StandardGame.Master,
  val xPos: Double,
  val yPos: Double,
  val width: Double)
    extends GameEntity[StandardGame.Master](master) {

  val healthColor = Color.red
  val energyColor = new Color(0x00bfff)

  val health = {
    def onChangeValue(x: Double): Unit = {
      val rect0 = master.player.drawRect
      if (!master.silent)
        master.particleText.addParticle(f"${x}%+.1f", healthColor.darker, rect0, (-90.0, 45.0))
    }
    def onChangeMax(x: Double): Unit = {}
    new BarStat(100, 100, StatDrawParams(width, 100, healthColor), onChangeValue, onChangeMax)
  }
  val energy = new BarStat(100, 100, StatDrawParams(width, 100, energyColor))

  object RefreshStep extends AbstractStepEvent(PlayerMeter.this) {
    override def call() = {
      health.refresh()
      energy.refresh()
    }
  }

  def rect: Rectangle =
    Rectangle(
      xPos,
      yPos,
      xPos + math.max(health.Draw.width, energy.Draw.width),
      yPos + health.Draw.height + energy.Draw.height
    )

  override def draw(graph: Graphics2D): Unit = {
    val health = this.health.Draw
    val energy = this.energy.Draw
    val y0 = yPos
    val y1 = yPos + health.height
    health.draw(graph, Rectangle(xPos, y0, xPos + health.width, y0 + health.height))
    energy.draw(graph, Rectangle(xPos, y1, xPos + energy.width, y1 + energy.height))
  }

  stepEvent += RefreshStep

}
