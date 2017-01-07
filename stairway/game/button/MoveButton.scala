
package com.mercerenies.stairway.game.button

import com.mercerenies.stairway.game.ButtonPad
import com.mercerenies.stairway.action.KeyboardKey
import java.awt.event.KeyEvent.VK_Q

class MoveButton(pad: ButtonPad) extends Button(pad, 0, KeyboardKey(VK_Q)) {

  override def call(): Unit = {
    super.call()
    if (master.isIdle && isPressed)
      master.stepForward()
  }

}
