
package com.mercerenies.stairway
package game.button

import game.ButtonPad
import game.attack.PhysicalAttack
import action.KeyboardKey
import luck.Probability
import java.awt.event.KeyEvent.VK_A

class PhysicalButton(pad: ButtonPad) extends Button(pad, 3, KeyboardKey(VK_A)) {

  override def call(): Unit = {
    super.call()
    if (master.isIdle && master.isInCombat && isPressed) {
      new PhysicalAttack(master).perform()
      master.stepForward()
    }
  }

  private def augmentedCrit = master.luck.augmentedOdds(master.stats.criticalChance)

  override def buttonDesc = s"Physical Attack\nDeal damage equal to ATK; chance of critical hit dealing triple damage [Odds of Critical Hit: ${Probability(augmentedCrit)}]"

}
