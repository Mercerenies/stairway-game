
package com.mercerenies.stairway.game.attack

import com.mercerenies.stairway.game.StandardGame
import com.mercerenies.stairway.enemy.{Enemy, HealthBased}
import java.awt.Color

class KnifeAttack(master: StandardGame.Master) extends PlayerAttack(master) {

  override def damage(enemy: Enemy): Double = master.stats.attackPower

}
