
package com.mercerenies.stairway
package enemy

import game.{Player, StandardGame}
import status.WingedEffect

class Birdbrain(master: StandardGame.Master, entropy: Enemy.Entropy)
    extends SingleEnemy(master)
    with TurnCounterEnemy {

  override def spoils: Spoils = Spoils.Money((35 + 4 * entropy.reward).toInt)

  override def startingHealth: Double = 20.0 + 2.0 * entropy.risk

  override def attackPower: Double = 4.0 + 1.0 * entropy.risk

  override def imageIndex: Int = 4

  afflictStatus(new WingedEffect(None))

}
