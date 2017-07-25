
package com.mercerenies.stairway.product.item

import com.mercerenies.stairway.product._
import com.mercerenies.stairway.image.ItemsImage
import com.mercerenies.stairway.game.Player
import com.mercerenies.stairway.space.BowlingSpace
import java.awt.Image

case object BowlingBall extends Item {

  override def canBeUsed(player: Player): Boolean = true

  override def name: String = "Bowling Ball"

  override def description: String = "Obliterate the next three spaces"

  override def index: Int = 5

  override def isPassive: Boolean = false

  override def basePrice: Int = 23

  override def use(player: Player): Unit = {
    val feed = player.master.belt.feed
    player nextNPositions 3 foreach { pos =>
      feed.assignOverride(pos, BowlingSpace)
    }
  }

}
