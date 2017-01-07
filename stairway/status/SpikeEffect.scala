
package com.mercerenies.stairway.status

import com.mercerenies.stairway.game.Player
import com.mercerenies.stairway.game.attack.SpikeAttack
import com.mercerenies.stairway.enemy.Enemy

class SpikeEffect(val damage: Double, length: Int = 5) extends StatusEffect(Some(length)) {

  override def imageIndex: Int = 0

  def onEffect(obj: StatusEffect.Effectee): Unit = obj match {
    case Left(player) => player.takeDamage(damage)
    case Right(enemy) => enemy.takeDamage(new SpikeAttack(enemy.master, damage))
  }

}
