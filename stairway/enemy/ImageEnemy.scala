
package com.mercerenies.stairway.enemy

import com.mercerenies.stairway.util.Rectangle
import java.awt.{Graphics2D, Image}

trait ImageEnemy extends Enemy {

  def imageIndex: Int

  def image: Image = Enemy.image.enemy(imageIndex)

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
    val rect0 = this.rect
    graph.drawImage(
      image,
      rect0.x.toInt,
      rect0.y.toInt,
      rect0.width.toInt,
      rect0.height.toInt,
      null
    )
    super.draw(graph, rect)
  }

}
