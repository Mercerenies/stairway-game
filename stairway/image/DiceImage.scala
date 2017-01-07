
package com.mercerenies.stairway.image

import com.mercerenies.stairway.luck.DiceValue

class DiceImage extends ImageResource("./res/dice.png") {

  def die(n: Int) = subimage(DiceImage.Width * n, 0, DiceImage.Width, DiceImage.Height)

  def dieNumber(n: DiceValue) = die((n.value - 1) % 6)

  def minRolling: Int = 6
  def maxRolling: Int = 16

}

object DiceImage {
  val Width = 32
  val Height = 32
}
