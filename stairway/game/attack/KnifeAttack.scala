
package com.mercerenies.stairway
package game.attack

import game.StandardGame
import enemy.{Enemy, HealthBased}
import java.awt.Color

class KnifeAttack(master: StandardGame.Master) extends PlayerAttack(master) {

  override def damage(enemy: Enemy): Double = master.stats.attackPower

}
