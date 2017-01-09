
package com.mercerenies.stairway.enemy

import com.mercerenies.stairway.util.Rectangle
import com.mercerenies.stairway.stat.{BarStat, StatDrawParams}
import com.mercerenies.stairway.game.attack.PlayerAttack
import java.awt.{Color, Graphics2D}

trait HealthBased extends Enemy {

  val healthColor: Color = Color.green

  private val _health: BarStat = new BarStat(
    startingHealth,
    startingHealth,
    StatDrawParams(drawingWidth, 100, healthColor),
    onChangeHealth(_),
    (_) => {}
  )

  private def onChangeHealth(x: Double): Unit = {
    master.particleText.addParticle(f"${x}%+.1f", HealthBased.particleColor, rect)
  }

  protected def healthBar: BarStat = _health

  def health: Double = _health.value.toDouble

  def startingHealth: Double

  def shouldDrawHealth: Boolean = isBoss

  def drawingWidth: Double = this.rect.width

  override def isAlive: Boolean = health > 0

  override def takeDamage(attack: PlayerAttack): Unit = {
    healthBar.value -= attack.damage
    super.takeDamage(attack)
  }

  override def instantKill(): Unit = {
    healthBar.value = 0
  }

  override def step(): Unit = {
    super.step()
    healthBar.Draw.refresh()
  }

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
    if (shouldDrawHealth) {
      val (xPos, yPos) = (this.rect.xmin, this.rect.ymax)
      val drawer = healthBar.Draw
      val rect1 = Rectangle(xPos, yPos, xPos + drawer.width, yPos + drawer.height)
      drawer.draw(graph, rect1)
    }
    super.draw(graph, rect)
  }

}

object HealthBased {
  val particleColor = Color.red.darker
}
