
package com.mercerenies.stairway
package space

import game.StandardGame
import status.BlockedEffect

case class BlockedSpace(length: Int = 10) extends ImageSpace {

  override def imageIndex: Int = 24

  override def onLand(master: StandardGame.Master) = {
    master.player.afflictStatus(new BlockedEffect(length))
  }

  override def onEmulate(master: StandardGame.Master) = {}

}
