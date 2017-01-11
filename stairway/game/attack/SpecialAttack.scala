
package com.mercerenies.stairway.game.attack

import com.mercerenies.stairway.game.StandardGame
import com.mercerenies.stairway.enemy.Enemy
import com.mercerenies.stairway.util
import java.awt.Color

class SpecialAttack(master: StandardGame.Master) extends PlayerAttack(master) {

  override def damage(enemy: Enemy): Double = master.stats.specialMultiplier * master.stats.attackPower

  def canPerform: Boolean = master.meter.energy.value.toDouble >= master.stats.specialAttackCost

  override def attackUsed(enemy: Enemy): Unit = {
    master.meter.energy.value -= master.stats.specialAttackCost
  }

}
