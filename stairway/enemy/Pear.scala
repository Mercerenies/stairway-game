
package com.mercerenies.stairway.enemy

import com.mercerenies.stairway.game.{Player, StandardGame}

class Pear(master: StandardGame.Master, entropy: Enemy.Entropy)
    extends SingleEnemy(master) {

  override def spoils: Spoils = Spoils.Money((28 + 4 * entropy.reward).toInt)

  override def startingHealth: Double = 13.0 + 4.0 * entropy.risk

  override def attackPower: Double = 4.0 + 1.0 * entropy.risk

  override def imageIndex: Int = 11

  override def attack(player: Player): Unit = {
    super.attack(player)
    player.master.damage.advance(2.0)
  }

}
