
package com.mercerenies.stairway
package status

import game.Player
import game.attack.SpikeAttack
import enemy.Enemy

class SpicyEffect(val power: Int = 1, length: Int = 10) extends StatusEffect(Some(length)) {

  override def imageIndex: Int = 1

  override def policy: EffectPolicy = EffectPolicy.FirstTarget

  override def attackModifier: Int = power

  override def onEffect(obj: StatusEffect.Effectee): Unit = {
    // Passive effect
  }

}
