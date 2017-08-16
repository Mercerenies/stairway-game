
package com.mercerenies.stairway
package enemy

import game.{Player, StandardGame}
import util.tap._

class Spider(master: StandardGame.Master, entropy: Enemy.Entropy)
    extends SingleEnemy(master) {

  override def spoils: Spoils = Spoils.Money((33 + 6 * entropy.reward).toInt)

  override def startingHealth: Double = 11.0 + 1.0 * entropy.risk

  override def attackPower: Double = 7.0 + 1.0 * entropy.risk

  override def imageIndex: Int = 2

  override def attack(player: Player): Option[Double] = {
    super.attack(player).tap { atk =>
      atk.foreach { _ =>
        player.master.meter.energy.value -= 5
      }
    }
  }

  override def name = "Spider"

  override def desc = "Drains your energy on every attack"

}
