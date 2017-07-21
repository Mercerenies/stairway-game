
package com.mercerenies.stairway
package game.attack

import game.StandardGame
import enemy.{Enemy, HealthBased}
import java.awt.Color

class ArrowAttack(master: StandardGame.Master) extends PlayerAttack(master) {

  override def damage(enemy: Enemy): Double =
    if (enemy.isFlying)
      master.stats.attackPower * 2
    else
      master.stats.attackPower

  override def nature: AttackNature = AttackNature(FlightLevel.Special)

}
