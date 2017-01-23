
package com.mercerenies.stairway.enemy

import com.mercerenies.stairway.game.{Player, StandardGame}

class Arachula(master: StandardGame.Master)
    extends SingleEnemy(master)
    with BossEnemy {

  override def bossName: String = "Count Arachula"

  override def spoils: Spoils = Spoils.Money(65) + Spoils.Strength

  override def startingHealth: Double = 90.0

  override def attackPower: Double = 9.0

  override def imageIndex: Int = 22

  override def attack(player: Player): Unit = {
    super.attack(player)
    player.master.meter.energy.value -= 5
  }

}
