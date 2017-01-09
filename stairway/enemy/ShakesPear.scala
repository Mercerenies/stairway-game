
package com.mercerenies.stairway.enemy

import com.mercerenies.stairway.game.{Player, StandardGame}

class ShakesPear(master: StandardGame.Master)
    extends SingleEnemy(master)
    with BossEnemy {

  override def bossName: String = "William Shakes Pear"

  override def spoils: Spoils = Spoils.Money(55) + Spoils.Strength

  override def startingHealth: Double = 60.0

  override def attackPower: Double = 7.0

  override def imageIndex: Int = 26

  override def attack(player: Player): Unit = {
    super.attack(player)
    player.master.damage.advance(2.0)
  }

}
