
package com.mercerenies.stairway.game.attack

import com.mercerenies.stairway.game.{StandardGame, Player}
import com.mercerenies.stairway.enemy.Enemy

class FragmentedAttack(val attack: PlayerAttack, val parts: Int)
    extends PlayerAttack(attack.master) {

  override def damage: Double = attack.damage / parts

  override def attackUsed(enemy: Enemy): Unit = {
    attack.attackUsed(enemy)
  }

}
