
package com.mercerenies.stairway.image

import com.mercerenies.stairway.game.belt.ConveyerBelt

class SpacesImage extends ImageResource("./res/spaces.png") {

  def space(n: Int) =
    subimage(SpacesImage.Width * n, 0, SpacesImage.Width, SpacesImage.Height)

}

object SpacesImage {
  val Width = ConveyerBelt.SpaceDim.width
  val Height = ConveyerBelt.SpaceDim.height
}
