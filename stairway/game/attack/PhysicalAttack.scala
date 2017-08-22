
package com.mercerenies.stairway.game.attack

import com.mercerenies.stairway.game.StandardGame
import com.mercerenies.stairway.enemy.{Enemy, HealthBased}
import java.awt.Color

class PhysicalAttack(master: StandardGame.Master) extends PlayerAttack(master) {
  import PhysicalAttack._

  val isCritical: Boolean = {
    master.luck.evaluateLuck((LuckWeightMinus, LuckWeightPlus), master.stats.criticalChance)
  }

  override def damage(enemy: Enemy): Double =
    if (isCritical)
      master.stats.attackPower * 3
    else
      master.stats.attackPower

  override def attackUsed(enemy: Enemy): Unit = {
    if (isCritical)
      master.particleText.addParticle("CRIT!", HealthBased.particleColor, enemy.rect)
  }

}

object PhysicalAttack {
  val LuckWeightMinus = 0.010
  val LuckWeightPlus  = 0.005
}
