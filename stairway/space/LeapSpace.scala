
package com.mercerenies.stairway.space

import com.mercerenies.stairway.game.StandardGame

case class LeapSpace(spaces: Int = 2) extends ImageSpace {

  override def imageIndex: Int = 23

  override def onLand(master: StandardGame.Master) = {
    master.stepForward(spaces)
  }

  override def onEmulate(master: StandardGame.Master) = {}

}
