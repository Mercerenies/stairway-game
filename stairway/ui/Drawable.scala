
package com.mercerenies.stairway.ui

import java.awt.{Graphics, Graphics2D}
import com.mercerenies.stairway.util.Rectangle

/** A drawable object.
  *
  * Drawable objects can, as the name implies, be drawn to the screen.
  */
trait Drawable {

  /** Draws the object to the screen in the given rectangle.
    *
    * @param graph the graphics object
    * @param rect the bounding box
    */
  def draw(graph: Graphics2D, rect: Rectangle): Unit

}
