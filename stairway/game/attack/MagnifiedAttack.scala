
package com.mercerenies.stairway.game.attack

import com.mercerenies.stairway.game.{StandardGame, Player}
import com.mercerenies.stairway.enemy.Enemy

class MagnifiedAttack(val attack: PlayerAttack, val magnitude: Double)
    extends PlayerAttack(attack.master) {

  override def damage(enemy: Enemy): Double = attack.damage(enemy) * magnitude

  override def nature = attack.nature

  override def attackUsed(enemy: Enemy): Unit = {
    attack.attackUsed(enemy)
  }

}
