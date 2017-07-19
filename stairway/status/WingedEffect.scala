
package com.mercerenies.stairway
package status

import game.Player
import enemy.Enemy

class WingedEffect(length: Option[Int] = Some(10)) extends StatusEffect(length) {

  def this(length: Int) = this(Some(length))

  override def imageIndex: Int = 2

  override def policy: EffectPolicy = EffectPolicy.FirstTarget

  override def isFlying: Boolean = true

  override def onEffect(obj: StatusEffect.Effectee): Unit = {
    // Passive effect
  }

}
