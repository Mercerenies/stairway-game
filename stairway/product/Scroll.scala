
package com.mercerenies.stairway.product

import com.mercerenies.stairway.game.StandardGame
import com.mercerenies.stairway.stat.ImprovableStats
import com.mercerenies.stairway.ui.SizedDrawable
import com.mercerenies.stairway.util
import com.mercerenies.stairway.util.Rectangle
import java.awt.{Graphics2D, Font, Color}

class Scroll(val effects: Scroll.Effect*)
    extends SizedDrawable {
  import util.GraphicsImplicits._

  private var _age = 1
  private var _used = false

  def age: Int = _age

  def age_=(x: Int): Unit = {
    _age = util.clamp(x, 1, effects.size)
  }

  def effect: Scroll.Effect = effects(age - 1)

  def used = _used

  def click(master: StandardGame.Master): Unit = {
    effect.perform(master)
    _used = true
  }

  override def dims: (Double, Double) = (80, 100)

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {

    graph.setColor(Color.white)
    graph.fill(rect)
    graph.setColor(Color.black)
    graph.draw(rect)

    graph.setFont(Scroll.DefaultFont)
    graph.drawString(
      effect.caption,
      util.lerp(rect.xmin, rect.xmax, 0.10),
      util.lerp(rect.ymin, rect.ymax, 0.50)
    )

  }

}

object Scroll {

  val DefaultFont = new Font(Font.SERIF, Font.BOLD, 12)

  case class Effect(caption: String, result: (StandardGame.Master) => Unit) {
    def perform(master: StandardGame.Master) = result(master)
  }

  object LevelEffect {
    def apply(caption: String, result: (ImprovableStats) => Unit) =
      Effect(caption, m => result(m.stats.levels))
  }

}
