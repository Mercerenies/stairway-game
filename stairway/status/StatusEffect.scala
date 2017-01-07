
package com.mercerenies.stairway.status

import com.mercerenies.stairway.image.StatusesImage
import com.mercerenies.stairway.game.Player
import com.mercerenies.stairway.enemy.Enemy
import com.mercerenies.stairway.product.Captioned
import com.mercerenies.stairway.util.Rectangle
import java.awt.{Graphics2D, Image}

abstract class StatusEffect(val length: Option[Int]) // If None, the effect does not expire
    extends Captioned {
  import StatusEffect.Effectee

  private var turnCounter: Int = 0

  def this(length: Int) = this(Some(length))

  def imageIndex: Int

  override def image: Image =
    StatusEffect.image.status(imageIndex)

  def expired: Boolean = length match {
    case None => false
    case Some(n) => turnCounter >= n
  }

  def onEffect(obj: Effectee): Unit

  def perform(obj: Effectee): Unit = {
    onEffect(obj)
    turnCounter += 1
  }

  override def dims: (Double, Double) = (StatusesImage.Width, StatusesImage.Height)

}

object StatusEffect {

  type Effectee = Either[Player, Enemy]

  val image = new StatusesImage

}
