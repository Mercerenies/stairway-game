
package com.mercerenies.stairway.enemy

import com.mercerenies.stairway.game.{Player, StandardGame}

class Rat(master: StandardGame.Master, entropy: Enemy.Entropy)
    extends SingleEnemy(master) {

  override def spoils: Spoils = Spoils.Money((16 + 2 * entropy.reward).toInt)

  override def startingHealth: Double = 5.0 + 1.0 * entropy.risk

  override def attackPower: Double = 3.0

  override def imageIndex: Int = 0

}
