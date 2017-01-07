
package com.mercerenies.stairway.game.button

import com.mercerenies.stairway.game.ButtonPad
import com.mercerenies.stairway.game.attack.PhysicalAttack
import com.mercerenies.stairway.action.KeyboardKey
import java.awt.event.KeyEvent.VK_A

class PhysicalButton(pad: ButtonPad) extends Button(pad, 3, KeyboardKey(VK_A)) {

  override def call(): Unit = {
    super.call()
    if (master.isIdle && master.isInCombat && isPressed) {
      new PhysicalAttack(master).perform()
      master.stepForward()
    }
  }

}
