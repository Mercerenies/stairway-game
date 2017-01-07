
package com.mercerenies.stairway.image

class StatusesImage extends ImageResource("./res/statuses.png") {

  def status(n: Int) = subimage(StatusesImage.Width * n, 0, StatusesImage.Width, StatusesImage.Height)

}

object StatusesImage {
  val Width = 16
  val Height = 16
}
