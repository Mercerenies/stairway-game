
package com.mercerenies.stairway
package game.attack

import game.StandardGame
import enemy.{Enemy, HealthBased}
import java.awt.Color

class SelfDestructAttack(master: StandardGame.Master, dmg: Double) extends PlayerAttack(master) {

  override def damage(enemy: Enemy): Double = dmg

  override def nature: AttackNature = AttackNature(FlightLevel.Special)

}
