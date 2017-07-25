
package com.mercerenies.stairway
package enemy

import space.{Space, EnemySpace, SpilledMilkSpace}
import game.{Player, StandardGame}

class Robot(master: StandardGame.Master, entropy: Enemy.Entropy)
    extends SingleEnemy(master) {

  override def spoils: Spoils = Spoils.Money((45 + 2 * entropy.reward).toInt)

  override def startingHealth: Double = 30.0 + 3.0 * entropy.risk

  override def attackPower: Double = 15.0 + 1.0 * entropy.risk

  override def imageIndex: Int = 8

}
