
package com.mercerenies.stairway.debug

import com.mercerenies.stairway.game.ButtonPad
import com.mercerenies.stairway.game.button._
import com.mercerenies.stairway.action.KeyboardKey
import java.awt.event.KeyEvent.VK_E

class EmulateButton(pad: ButtonPad) extends Button(pad, 2, KeyboardKey(VK_E)) {

  override def call(): Unit = {
    super.call()
    if (DebugMode.enabled && master.isIdle && isPressed)
      master.leapForward()
  }

  override def buttonDesc = "???\n???"

}
