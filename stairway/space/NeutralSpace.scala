
package com.mercerenies.stairway.space

import com.mercerenies.stairway.game.StandardGame

case object NeutralSpace extends ImageSpace {

  override def imageIndex: Int = 0

  override def onLand(master: StandardGame.Master) = {}

  override def name = "Neutral Space"

  override def desc = "No special effect"

}
