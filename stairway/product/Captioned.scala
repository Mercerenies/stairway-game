
package com.mercerenies.stairway.product

import com.mercerenies.stairway.ui.SizedDrawable
import com.mercerenies.stairway.util.Rectangle
import java.awt._

trait Captioned extends SizedDrawable {

  def image: Image

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
    graph.drawImage(
      image,
      rect.x.toInt,
      rect.y.toInt,
      rect.width.toInt,
      rect.height.toInt,
      null
    )
  }

}
