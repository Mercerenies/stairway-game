
package com.mercerenies.stairway.space

import java.awt.Graphics2D
import com.mercerenies.stairway.util.Rectangle
import com.mercerenies.stairway.game.StandardGame

case object EmptySpace extends Space {

  override def onLand(master: StandardGame.Master) = {}

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {}

  override def name = "Empty Space"

  override def desc = "No special effect"

}
