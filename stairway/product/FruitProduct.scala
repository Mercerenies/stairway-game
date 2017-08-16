
package com.mercerenies.stairway
package product

import image.FruitsImage
import game.tagline.Tagged

abstract class FruitProduct extends Purchasable with Usable with Captioned with Tagged {

  override def dims: (Double, Double) = (FruitsImage.Width, FruitsImage.Height)

  def toString(): String

}
