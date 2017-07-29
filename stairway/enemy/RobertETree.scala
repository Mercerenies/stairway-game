
package com.mercerenies.stairway
package enemy

import game.{Player, StandardGame}
import space.RootSpace
import util.tap._

class RobertETree(master: StandardGame.Master)
    extends SingleEnemy(master)
    with BossEnemy
    with TurnCounterEnemy {

  override def bossName: String = "Robert E Tree"

  override def spoils: Spoils =
    Spoils.Money(130) + Spoils.Strength + Spoils.Resilience

  override def startingHealth: Double = 165.0

  override def attackPower: Double = 15.0

  override def imageIndex: Int = 25

  private def rootCount: Int = health match {
    case x if x <  25.0 => 5
    case x if x <  50.0 => 4
    case x if x < 100.0 => 3
    case _              => 2
  }

  override def counterStart = 0

  override def attack(player: Player): Option[Double] = {
    (if (isEndOfCycle(RobertETree.attackCycle)) {
      // Use the root attack
      val belt = master.belt
      val matching = player.nextPositions drop 2 grouped 3 map { _.head } take rootCount
      matching foreach { idx =>
        belt.feed.assignOverride(idx, RootSpace())
      }
      master.particleText.addParticle("Spread!", Enemy.particleColor, rect)
      Some(0.0)
    } else {
      // Perform the standard attack
      super.attack(player).tap { atk =>
        atk.foreach { dmg =>
          player.master.field.rootEnergy += dmg * RobertETree.damageMult
        }
      }
    }) tap { _ =>
      advanceCounter()
      val field = player.master.field
      val heal = List(
        field.rootEnergy,
        healthBar.max.toDouble - healthBar.value.toDouble,
        RobertETree.maxHeal
      ).min
      field.rootEnergy -= heal
      healthBar.value += heal
    }
  }

}

object RobertETree {
  val attackCycle = 8
  val damageMult = 0.05
  val maxHeal = 5.0
}
