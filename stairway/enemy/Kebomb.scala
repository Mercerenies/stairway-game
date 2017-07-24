
package com.mercerenies.stairway
package enemy

import game.{Player, StandardGame}
import game.attack.{PlayerAttack, SelfDestructAttack}

class Kebomb(master: StandardGame.Master, entropy: Enemy.Entropy)
    extends SingleEnemy(master) {

  private var _shattered = false

  override def spoils: Spoils = Spoils.Money((33 + 3 * entropy.reward).toInt)

  override def startingHealth: Double = 13 + 1.0 * entropy.risk

  override def attackPower: Double = 25 + 1.0 * entropy.risk

  override def imageIndex: Int = 32

  override def attack(player: Player): Unit = {
    super.attack(player)
    this.takeDamage(new SelfDestructAttack(player.master, attackPower))
  }

}
