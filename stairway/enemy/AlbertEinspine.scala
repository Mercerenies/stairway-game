
package com.mercerenies.stairway
package enemy

import game.{Player, StandardGame}
import game.attack.{PlayerAttack, AttackNature, EnemyAttack, FlightLevel}
import space.SpikeSpace
import util.tap._

class AlbertEinspine(master: StandardGame.Master)
    extends SingleEnemy(master)
    with BossEnemy
    with TurnCounterEnemy {

  override def bossName: String = "Albert Einspine"

  override def spoils: Spoils =
    Spoils.Money(177) + Spoils.Strength + Spoils.Resilience

  override def startingHealth: Double = 180.0

  override def attackPower: Double = 17.0

  override def imageIndex: Int = 40

  override def counterStart = 0

  override def attack(player: Player): Option[Double] = {
    (if (isEndOfCycle(AlbertEinspine.attackCycle)) {
      // Use the spike attack
      val belt = master.belt
      val matching = player.nextPositions(2)
      belt.feed.assignOverride(matching, SpikeSpace())
      master.particleText.addParticle("Spike!", Enemy.particleColor, rect)
      Some(0.0)
    } else {
      // Perform the standard attack
      super.attack(player)
    }) tap { _ =>
      advanceCounter()
    }
  }

  override def takeDamage(attack: PlayerAttack): Unit = {
    master.player.takeDamage(EnemyAttack(
      attackPower * AlbertEinspine.damageMult,
      AttackNature(FlightLevel.Special)
    ))
    super.takeDamage(attack)
  }

}

object AlbertEinspine {
  val attackCycle = 8
  val damageMult = 0.5
}
