
package com.mercerenies.stairway.image

class ButtonsImage extends ImageResource("./res/buttons.png") {

  def buttonNeutral(n: Int) =
    subimage(ButtonsImage.Width * n, 0, ButtonsImage.Width, ButtonsImage.Height)

  def buttonPressed(n: Int) =
    subimage(ButtonsImage.Width * n, ButtonsImage.Height, ButtonsImage.Width, ButtonsImage.Height)

  def button(pressed: Boolean, n: Int) = pressed match {
    case false => buttonNeutral(n)
    case true => buttonPressed(n)
  }

}

object ButtonsImage {
  val Width = 48
  val Height = 48
}
