
package com.mercerenies.stairway.luck

import com.mercerenies.stairway.ui.Drawable
import com.mercerenies.stairway.util
import com.mercerenies.stairway.util.{Rectangle, Degrees}
import com.mercerenies.stairway.image.DiceImage
import java.awt._

class Die(
  val owner: DiceContainer,
  private val endingResult: DiceValue,
  private var xPos: Double,
  private var yPos: Double)
    extends Drawable {
  import util.RandomImplicits._
  import util.GraphicsImplicits._

  private var imageIndex: Either[Int, DiceValue] = Left(0)
  private var timer = util.rand.nextInt(60, 150)
  private val direction = util.rand.nextDouble(-112.5, -67.5)
  private val speed = util.rand.nextDouble(1.5, 2.5)

  def result: Option[DiceValue] =
    if (timer <= 0)
      Some(endingResult)
    else
      None

  def finished: Boolean = !result.isEmpty

  def image: Image = imageIndex match {
    case Left(n) => Die.image.die(n)
    case Right(n) => Die.image.dieNumber(n)
  }

  def rect: Rectangle =
    Rectangle(xPos, yPos, xPos + DiceImage.Width, yPos + DiceImage.Height)

  def step(): Unit = {
    if (timer > 0) {
      timer -= 1
      xPos += speed * math.cos(Degrees(direction))
      yPos += speed * math.sin(Degrees(direction))
      imageIndex = Left(util.rand.nextInt(Die.image.minRolling, Die.image.maxRolling))
    } else {
      imageIndex = Right(endingResult)
    }
  }

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
    graph.drawImage(image, rect)
  }

}

object Die {
  val image = new DiceImage
}
