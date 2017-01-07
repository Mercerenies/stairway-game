
package com.mercerenies.stairway.enemy

import com.mercerenies.stairway.game.{Player, StandardGame}

// Debug enemy
class EvilFace(master: StandardGame.Master)
    extends SingleEnemy(master) {

  override def spoils: Spoils = Spoils.None

  override def startingHealth: Double = 50.0

  override def attackPower: Double = 2.0

  override def imageIndex: Int = 29

}
