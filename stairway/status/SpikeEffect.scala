
package com.mercerenies.stairway
package status

import game.Player
import game.attack.SpikeAttack
import enemy.Enemy

class SpikeEffect(val damage: Double, length: Int = 5) extends StatusEffect(Some(length)) {

  override def imageIndex: Int = 0

  override def policy: EffectPolicy = EffectPolicy.Uniform

  override def onEffect(obj: StatusEffect.Effectee): Unit = obj match {
    case Left(player) => player.takeDamage(damage)
    case Right(enemy) => enemy.takeDamage(new SpikeAttack(enemy.master, damage))
  }

}
