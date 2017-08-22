
package com.mercerenies.stairway
package stat

import util.Rectangle
import util.GraphicsImplicits._
import game.{GameEntity, StandardGame}
import java.awt.{Graphics2D, Color}

class KarmaBar(
  master: StandardGame.Master,
  val rect: Rectangle)
    extends GameEntity[StandardGame.Master](master) {

  private var _value = master.luck.karma
  private var _frozen = false

  private val speed: Double = 0.01

  override def step() = {
    val karma = master.luck.karma
    if ((!_frozen) && (_value != karma))
      _value = util.toward(_value, karma, speed)
  }

  override def draw(graph: Graphics2D) = {

    val centerX = rect.centerX
    val karmaX = util.lerp(centerX, rect.xmax, _value)

    graph.setColor(KarmaBar.barColor)
    if (math.abs(_value) <= KarmaBar.epsilon)
      graph.draw(Rectangle(centerX, rect.ymin, karmaX, rect.ymax))
    else
      graph.fill(Rectangle(centerX, rect.ymin, karmaX, rect.ymax))
    graph.setColor(Color.black)
    graph.draw(rect)

  }

  def freeze(): Unit = {
    _frozen = true
  }

  def thaw(): Unit = {
    _frozen = false
  }

}

object KarmaBar {
  def karmaBounds = (-1.0, 1.0)
  def minKarma = karmaBounds._1
  def maxKarma = karmaBounds._2

  def epsilon = 0.01

  val barColor = new Color(0xA5, 0x2A, 0x2A)

}
