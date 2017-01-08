
package com.mercerenies.stairway.product

import com.mercerenies.stairway.ui.SizedDrawable
import com.mercerenies.stairway.util.{Rectangle, GraphicsImplicits}
import java.awt._

trait Captioned extends SizedDrawable {
  import GraphicsImplicits._

  def image: Image

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
    graph.drawImage(image, rect)
  }

}
