
package com.mercerenies.stairway
package enemy

import game.{Player, StandardGame}
import space.SpilledMilkSpace
import util.tap._

class GenghisKone(master: StandardGame.Master)
    extends SingleEnemy(master)
    with BossEnemy
    with TurnCounterEnemy {

  override def bossName: String = "Genghis Kone"

  override def spoils: Spoils = Spoils.Money(90) + Spoils.Strength + Spoils.Resilience

  override def startingHealth: Double = 120.0

  override def attackPower: Double = 11.0

  override def imageIndex: Int = 24

  private def spreadLength: Int = health match {
    case x if x < 25.0 => 8
    case x if x < 50.0 => 5
    case _             => 4
  }

  override def counterStart = GenghisKone.attackCycle / 2

  override def attack(player: Player): Option[Double] = {
    (if (isEndOfCycle(GenghisKone.attackCycle)) {
      // Use the ice cream attack
      val belt = master.belt
      val matching = player.nextPositions.slice(3, 3 + spreadLength)
      matching foreach { idx =>
        belt.feed.assignOverride(idx, SpilledMilkSpace)
      }
      master.particleText.addParticle("Splash!", Enemy.particleColor, rect)
      Some(0.0)
    } else {
      // Perform the standard attack
      super.attack(player)
    }) tap { _ =>
      advanceCounter()
    }
  }

  override def name = bossName

  override def desc = "Secondary attack creates several Spilled Milk Spaces"

}

object GenghisKone {
  val attackCycle = 7
}
