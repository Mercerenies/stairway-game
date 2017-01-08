
package com.mercerenies.stairway.util

import java.awt.{Graphics2D, Image}

object GraphicsImplicits {

  implicit class RichGraphics(val graph: Graphics2D) extends AnyVal {

    def drawString(s: String, x: Double, y: Double) = {
      graph.drawString(s, x.toFloat, y.toFloat)
    }

    def drawImage(image: Image, rect: Rectangle) = {
      graph.drawImage(image, rect.x.toInt, rect.y.toInt, rect.width.toInt, rect.height.toInt, null)
    }

  }

}
