
package com.mercerenies.stairway.ui

import java.awt.{Graphics, Graphics2D}
import com.mercerenies.stairway.util.Rectangle

trait Drawable {

  def draw(graph: Graphics2D, rect: Rectangle): Unit

}
