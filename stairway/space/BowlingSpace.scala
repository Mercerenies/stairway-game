
package com.mercerenies.stairway.space

import com.mercerenies.stairway.game.StandardGame

case object BowlingSpace extends ImageSpace {

  override def imageIndex: Int = 10

  override def onLand(master: StandardGame.Master) = {}

}
