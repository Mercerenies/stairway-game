
package com.mercerenies.stairway
package status

import game.Player
import enemy.Enemy

class BlockedEffect(length: Int = 10) extends StatusEffect(Some(length)) {

  override def imageIndex: Int = 3

  override def policy: EffectPolicy = EffectPolicy.Distributed

  override def canUseItems: Boolean = false

  override def onEffect(obj: StatusEffect.Effectee): Unit = {
    // Passive effect
  }

}
