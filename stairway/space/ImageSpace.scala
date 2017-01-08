
package com.mercerenies.stairway.space

import java.awt.Graphics2D
import com.mercerenies.stairway.util.{Rectangle, GraphicsImplicits}

trait ImageSpace extends Space {
  import GraphicsImplicits._

  def imageIndex: Int

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
    graph.drawImage(Space.image space imageIndex, rect)
  }

}
