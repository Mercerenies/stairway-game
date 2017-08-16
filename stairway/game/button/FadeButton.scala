
package com.mercerenies.stairway.game.button

import com.mercerenies.stairway.game.ButtonPad
import com.mercerenies.stairway.action.KeyboardKey
import java.awt.event.KeyEvent.VK_W

class FadeButton(pad: ButtonPad) extends Button(pad, 1, KeyboardKey(VK_W)) {

  override def call(): Unit = {
    super.call()
    if (master.isIdle && isPressed && master.canFadeForward)
      master.fadeForward()
  }

  override def buttonDesc = "Fade\nMove forward one space but ignore effects; costs energy"

}
