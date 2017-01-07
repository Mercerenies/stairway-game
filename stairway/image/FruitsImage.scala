
package com.mercerenies.stairway.image

class FruitsImage extends ImageResource("./res/fruits.png") {

  def fruit(n: Int) = subimage(FruitsImage.Width * n, 0, FruitsImage.Width, FruitsImage.Height)

  def apple = fruit(0)
  def orange = fruit(1)
  def melon = fruit(2)
  def mystery = fruit(3)

}

object FruitsImage {
  val Width = 64
  val Height = 64
}
