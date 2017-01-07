
package com.mercerenies.stairway.space

import com.mercerenies.stairway.game.StandardGame

case object IceSpace extends ImageSpace {

  override def imageIndex: Int = 11

  override def onLand(master: StandardGame.Master) = {
    master.stepForward()
  }

}
