
package com.mercerenies.stairway.game.button

import com.mercerenies.stairway.game.ButtonPad
import com.mercerenies.stairway.product.Apple
import com.mercerenies.stairway.action.KeyboardKey
import java.awt.event.KeyEvent.VK_Z

class AppleButton(pad: ButtonPad) extends FruitButton(pad, 6, KeyboardKey(VK_Z)) {

  override def call(): Unit = {
    super.call()
    if (master.isIdle && justReleased && count > 0) {
      Apple.use(master.player)
      count -= 1
    }
  }

}
