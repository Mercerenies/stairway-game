
package com.mercerenies.stairway.space

import java.awt.{Graphics, Graphics2D, Color}
import com.mercerenies.stairway.ui.Drawable
import com.mercerenies.stairway.util.Rectangle
import com.mercerenies.stairway.image.SpacesImage
import com.mercerenies.stairway.game.StandardGame

trait Space extends Drawable {

  def isOverridable: Boolean = true

  def onLand(master: StandardGame.Master): Unit

  def onDepart(master: StandardGame.Master): Unit = {}

  def onEmulate(master: StandardGame.Master): Unit = {
    onLand(master)
    onDepart(master)
  }

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
    graph.setColor(Color.white)
    graph.fill(rect)
    graph.setColor(Color.black)
    graph.draw(rect)
  }

}

object Space {

  val image = new SpacesImage

}
