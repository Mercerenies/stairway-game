
package com.mercerenies.stairway.space

import com.mercerenies.stairway.util
import com.mercerenies.stairway.game.StandardGame
import com.mercerenies.stairway.luck.DiceValue
import com.mercerenies.stairway.product.Fruits
import java.awt.Color

case class FruitTheftSpace(val count: Int) extends ImageSpace {
  import util.RandomImplicits._

  override def imageIndex: Int = 19

  override def onLand(master: StandardGame.Master) = {
    val targets = List(
      master.buttonPad.appleButton,
      master.buttonPad.orangeButton,
      master.buttonPad.melonButton
    ) filter (_.count > 0)
    if (!targets.isEmpty) {
      val target = util.rand.nextOf(targets: _*)
      target.count -= 1
      master.particleText.addParticle(
        s"-1 ${target.product}",
        FruitTheftSpace.color,
        master.player.drawRect,
        (-90.0, 45.0)
      )
    }
  }

}

object FruitTheftSpace {
  val color = Color.orange.darker
}
