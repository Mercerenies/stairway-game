
package com.mercerenies.stairway.game.button

import com.mercerenies.stairway.game.ButtonPad
import com.mercerenies.stairway.game.attack.SpecialAttack
import com.mercerenies.stairway.action.KeyboardKey
import java.awt.event.KeyEvent.VK_D

class SpecialButton(pad: ButtonPad) extends Button(pad, 5, KeyboardKey(VK_D)) {

  override def call(): Unit = {
    super.call()
    if (master.isIdle && master.isInCombat && isPressed) {
      val atk = new SpecialAttack(master)
      if (atk.canPerform) {
        atk.perform()
        master.stepForward()
      }
    }
  }

  override def buttonDesc = "Special Attack\nDeal twice your ATK in damage; costs energy"

}
