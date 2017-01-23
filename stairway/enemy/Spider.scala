
package com.mercerenies.stairway.enemy

import com.mercerenies.stairway.game.{Player, StandardGame}

class Spider(master: StandardGame.Master, entropy: Enemy.Entropy)
    extends SingleEnemy(master) {

  override def spoils: Spoils = Spoils.Money((33 + 6 * entropy.reward).toInt)

  override def startingHealth: Double = 10.0 + 1.0 * entropy.risk

  override def attackPower: Double = 7.0 + 1.0 * entropy.risk

  override def imageIndex: Int = 2

  override def attack(player: Player): Unit = {
    super.attack(player)
    player.master.meter.energy.value -= 5
  }

}
