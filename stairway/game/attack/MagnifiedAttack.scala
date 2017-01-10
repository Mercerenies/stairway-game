
package com.mercerenies.stairway.game.attack

import com.mercerenies.stairway.game.{StandardGame, Player}
import com.mercerenies.stairway.enemy.Enemy

class MagnifiedAttack(val attack: PlayerAttack, val magnitude: Double)
    extends PlayerAttack(attack.master) {

  override def damage: Double = attack.damage * magnitude

  override def attackUsed(enemy: Enemy): Unit = {
    attack.attackUsed(enemy)
  }

}
