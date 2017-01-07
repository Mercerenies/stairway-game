
package com.mercerenies.stairway.image

class ItemsImage extends ImageResource("./res/items.png") {

  def emptyItem = item(0)

  def item(n: Int) = subimage(ItemsImage.Width * n, 0, ItemsImage.Width, ItemsImage.Height)

}

object ItemsImage {
  val Width = 32
  val Height = 32
}
