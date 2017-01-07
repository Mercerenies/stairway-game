
package com.mercerenies.stairway.space

import java.awt.Graphics2D
import com.mercerenies.stairway.util.Rectangle

trait ImageSpace extends Space {

  def imageIndex: Int

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
    graph.drawImage(
      Space.image space imageIndex,
      rect.x.toInt,
      rect.y.toInt,
      rect.width.toInt,
      rect.height.toInt,
      null
    )
  }

}
