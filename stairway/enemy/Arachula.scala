
package com.mercerenies.stairway
package enemy

import game.{Player, StandardGame}
import util.tap._

class Arachula(master: StandardGame.Master)
    extends SingleEnemy(master)
    with BossEnemy {

  override def bossName: String = "Count Arachula"

  override def spoils: Spoils = Spoils.Money(75) + Spoils.Strength + Spoils.Resilience

  override def startingHealth: Double = 95.0

  override def attackPower: Double = 9.0

  override def imageIndex: Int = 22

  override def attack(player: Player): Option[Double] = {
    super.attack(player).tap { atk =>
      atk.foreach { _ =>
        player.master.meter.energy.value -= 5
      }
    }
  }

  override def name = bossName

  override def desc = "Drains your energy on every attack"

}
