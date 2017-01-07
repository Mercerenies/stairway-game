
package com.mercerenies.stairway.image

class EnemiesImage extends ImageResource("./res/enemies.png") {

  def enemy(n: Int) = subimage(EnemiesImage.Width * n, 0, EnemiesImage.Width, EnemiesImage.Height)

}

object EnemiesImage {
  val Width = 128
  val Height = 128
}
