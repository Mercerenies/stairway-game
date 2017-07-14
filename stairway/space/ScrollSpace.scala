
package com.mercerenies.stairway.space

import com.mercerenies.stairway.game.StandardGame
import com.mercerenies.stairway.game.content.ScrollContent
import com.mercerenies.stairway.product.Scroll

case class ScrollSpace(age: Int, scrolls: Scroll*) extends ImageSpace {

  override def imageIndex: Int = 18

  override def isOverridable: Boolean = false

  override def onLand(master: StandardGame.Master) = {
    scrolls.foreach(_.age = age)
    master.contentArea.put(new ScrollContent(master.contentArea, scrolls))
  }

  override def onDepart(master: StandardGame.Master) = {
    master.contentArea.clear()
  }

  override def onEmulate(master: StandardGame.Master) = {
    scrolls.foreach(_.age = age)
    scrolls.find(!_.used).foreach(_.click(master))
  }

}
