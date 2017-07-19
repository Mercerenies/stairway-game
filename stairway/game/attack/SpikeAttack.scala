
package com.mercerenies.stairway
package game.attack

import game.StandardGame
import enemy.{Enemy, HealthBased}
import java.awt.Color

class SpikeAttack(master: StandardGame.Master, private val _damage: Double)
    extends PlayerAttack(master) {

  override def damage(enemy: Enemy): Double = _damage

  override def nature: AttackNature = AttackNature(FlightLevel.Grounded)

}
