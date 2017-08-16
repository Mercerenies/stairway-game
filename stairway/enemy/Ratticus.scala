
package com.mercerenies.stairway.enemy

import com.mercerenies.stairway.game.{Player, StandardGame}

class Ratticus(master: StandardGame.Master)
    extends SingleEnemy(master)
    with BossEnemy {

  override def bossName: String = "Ratticus Finch"

  override def spoils: Spoils = Spoils.Money(41) + Spoils.Strength

  override def startingHealth: Double = 35.0

  override def attackPower: Double = 5.0

  override def imageIndex: Int = 21

  override def name = bossName

  override def desc = "No special effect"

}
