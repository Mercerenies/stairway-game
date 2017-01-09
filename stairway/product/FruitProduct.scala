
package com.mercerenies.stairway.product

abstract class FruitProduct extends Purchasable with Usable with Captioned {

  override def dims: (Double, Double) = (FruitsImage.Width, FruitsImage.Height)

  def toString(): String

}
