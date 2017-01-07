
package com.mercerenies.stairway.game

import com.mercerenies.stairway.util.{Rectangle, lerp}
import java.awt.{Graphics2D, Color}

abstract class DamageFunctions(master: StandardGame.Master, val rect: Rectangle)
    extends GameEntity[StandardGame.Master](master) {

  def functions: Seq[DamageFunctions.Function]

  def stepSize: Double = 4.0

  override def draw(graph: Graphics2D): Unit = {
    graph.setColor(Color.black)
    graph.draw(rect)
    functions.foreach({ func =>
      graph.setColor(func.color)
      for (x <- 0.0 to (rect.width - stepSize) by stepSize) {
        val baseX = rect.xmin + x
        graph.drawLine(
          baseX.toInt,
          lerp(rect.ymax, rect.ymin, func(x)).toInt,
          (baseX + stepSize).toInt,
          lerp(rect.ymax, rect.ymin, func(x + stepSize)).toInt
        )
      }
    })
  }

}

object DamageFunctions {

  final case class Function(func: Double => Double, color: Color) {
    def apply(arg: Double) = func(arg)
  }

}
