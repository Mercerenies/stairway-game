
package com.mercerenies.stairway.enemy

import com.mercerenies.stairway.game.{Player, StandardGame}

class HelmetImp(master: StandardGame.Master, entropy: Enemy.Entropy)
    extends SingleEnemy(master) {

  override def spoils: Spoils = Spoils.Money((38 + 4 * entropy.reward).toInt)

  override def startingHealth: Double = 26.0 + 5.0 * entropy.risk

  override def attackPower: Double = 2.0 + 1.0 * entropy.risk

  override def imageIndex: Int = 3

}
