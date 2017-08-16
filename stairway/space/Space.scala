
package com.mercerenies.stairway
package space

import java.awt.{Graphics, Graphics2D, Color}
import ui.Drawable
import util.Rectangle
import image.SpacesImage
import game.StandardGame
import game.tagline.Tagged

trait Space extends Drawable with Tagged {

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

  override def tagText = Some(name + "\n" + desc)

  def name: String

  def desc: String

}

object Space {

  val image = new SpacesImage

}
