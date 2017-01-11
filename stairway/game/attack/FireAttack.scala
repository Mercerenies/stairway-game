
package com.mercerenies.stairway.game.attack

import com.mercerenies.stairway.game.StandardGame
import com.mercerenies.stairway.enemy.{Enemy, HealthBased}
import java.awt.Color

class FireAttack(master: StandardGame.Master) extends PlayerAttack(master) {

  override def damage(enemy: Enemy): Double =
    if (enemy.isUndead)
      master.stats.attackPower + 20
    else
      master.stats.attackPower

}
