
package com.mercerenies.stairway.enemy

import com.mercerenies.stairway.util.{Rectangle, GraphicsImplicits}
import java.awt.{Graphics2D, Image}

trait ImageEnemy extends Enemy {
  import GraphicsImplicits._

  def imageIndex: Int

  def image: Image = Enemy.image.enemy(imageIndex)

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
    graph.drawImage(image, rect)
    super.draw(graph, rect)
  }

}
