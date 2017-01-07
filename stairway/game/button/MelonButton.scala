
package com.mercerenies.stairway.game.button

import com.mercerenies.stairway.game.ButtonPad
import com.mercerenies.stairway.product.Melon
import com.mercerenies.stairway.action.KeyboardKey
import java.awt.event.KeyEvent.VK_C

class MelonButton(pad: ButtonPad) extends FruitButton(pad, 8, KeyboardKey(VK_C)) {

  override def call(): Unit = {
    super.call()
    if (master.isIdle && justReleased && count > 0) {
      Melon.use(master.player)
      count -= 1
    }
  }

}
