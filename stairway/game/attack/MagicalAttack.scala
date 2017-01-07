
package com.mercerenies.stairway.game.attack

import com.mercerenies.stairway.game.StandardGame
import com.mercerenies.stairway.enemy.Enemy
import com.mercerenies.stairway.util
import java.awt.Color

class MagicalAttack(master: StandardGame.Master) extends PlayerAttack(master) {

  override val damage: Double =
    util.lerp(0.5 * master.stats.attackPower, 1.5 * master.stats.attackPower, master.damage.magicValue)

  override protected def attackUsed(enemy: Enemy): Unit = {
    master.damage.advance()
  }

}
