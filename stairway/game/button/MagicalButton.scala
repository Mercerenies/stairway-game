
package com.mercerenies.stairway.game.button

import com.mercerenies.stairway.game.ButtonPad
import com.mercerenies.stairway.game.attack.MagicalAttack
import com.mercerenies.stairway.action.KeyboardKey
import java.awt.event.KeyEvent.VK_S

class MagicalButton(pad: ButtonPad) extends Button(pad, 4, KeyboardKey(VK_S)) {

  override def call(): Unit = {
    super.call()
    if (master.isIdle && master.isInCombat && isPressed) {
      new MagicalAttack(master).perform()
      master.stepForward()
    }
  }

  override def buttonDesc = "Magical Attack\nDeal damage based on your magic meter; advances the magic meter"

}
