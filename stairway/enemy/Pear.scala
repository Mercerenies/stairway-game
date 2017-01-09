
package com.mercerenies.stairway.enemy

import com.mercerenies.stairway.game.{Player, StandardGame}

class Pear(master: StandardGame.Master, entropy: Enemy.Entropy)
    extends SingleEnemy(master) {

  override def spoils: Spoils = Spoils.Money((20 + 3 * entropy.reward).toInt)

  override def startingHealth: Double = 10.0 + 4.0 * entropy.risk

  override def attackPower: Double = 5.0 + 1.0 * entropy.risk

  override def imageIndex: Int = 11

}
