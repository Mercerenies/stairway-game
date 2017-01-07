
package com.mercerenies.stairway.image

import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.io.File

class ImageResource(val filename: String) {

  lazy val resource: BufferedImage = ImageIO.read(new File(filename))

  def subimage(x: Int, y: Int, w: Int, h: Int): BufferedImage = resource.getSubimage(x, y, w, h)

}
