
package com.mercerenies.stairway.enemy

import com.mercerenies.stairway.game.{Player, StandardGame}

// Debug enemy
class EvilFaceBoss(master: StandardGame.Master)
    extends SingleEnemy(master)
    with BossEnemy {

  override def bossName: String = "Evil Face"

  override def spoils: Spoils = Spoils.None

  override def startingHealth: Double = 100.0

  override def attackPower: Double = 2.0

  override def imageIndex: Int = 30

  override def name = bossName

  override def desc = "Mostly harmless"

}
