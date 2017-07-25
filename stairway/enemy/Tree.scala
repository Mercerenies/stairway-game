
package com.mercerenies.stairway
package enemy

import space.{Space, EnemySpace, SpilledMilkSpace}
import game.{Player, StandardGame}

class Tree(master: StandardGame.Master, entropy: Enemy.Entropy)
    extends SingleEnemy(master) {

  override def spoils: Spoils = Spoils.Money((44 + 3 * entropy.reward).toInt)

  override def startingHealth: Double = 70.0 + 5.0 * entropy.risk

  override def attackPower: Double = 5.0

  override def imageIndex: Int = 7

}
