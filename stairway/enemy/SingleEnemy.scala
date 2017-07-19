
package com.mercerenies.stairway
package enemy

import game.{Player, StandardGame}
import game.attack.{EnemyAttack, AttackNature, FlightLevel}

abstract class SingleEnemy(override val master: StandardGame.Master)
    extends ImageEnemy
    with HealthBased {
  import SingleEnemy._

  def attackPower: Double

  def netAttackPower: Double = {
    statuses.map(_.attackModifier).foldLeft(attackPower) { _ + _ }
  }

  override def onDeath(player: Player): Unit = {
    super.onDeath(player)
    master.particleText.addParticle("KO!", Enemy.particleColor, rect)
  }

  override def attack(player: Player): Unit = {
    if (player.master.luck.evaluateLuck((LuckWeightMinus, LuckWeightPlus), master.stats.dodgeChance))
      master.particleText.addParticle(f"MISS!", HealthBased.particleColor, player.drawRect, (-90.0, 45.0))
    else
      player.takeDamage(EnemyAttack(netAttackPower, AttackNature(FlightLevel(isFlying))))
  }

}

object SingleEnemy {
  val LuckWeightMinus = 0.09
  val LuckWeightPlus = 0.001
}
