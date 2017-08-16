
package com.mercerenies.stairway.game.button

import com.mercerenies.stairway.game.ButtonPad
import com.mercerenies.stairway.product.Orange
import com.mercerenies.stairway.action.KeyboardKey
import java.awt.event.KeyEvent.VK_X

class OrangeButton(pad: ButtonPad) extends FruitButton(pad, 7, KeyboardKey(VK_X)) {

  override def product = Orange

  override def call(): Unit = {
    super.call()
    if (master.isIdle && justReleased && count > 0) {
      Orange.use(master.player)
      count -= 1
    }
  }

  override def buttonDesc = "Orange\nIncrease energy by your vitality"

}
