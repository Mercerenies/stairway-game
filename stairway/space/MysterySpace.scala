
package com.mercerenies.stairway.space

import com.mercerenies.stairway.util
import com.mercerenies.stairway.game.StandardGame
import com.mercerenies.stairway.game.content.MysteryContent
import com.mercerenies.stairway.luck.DiceValue
import com.mercerenies.stairway.product.Fruits

case class MysterySpace(val count: Int) extends ImageSpace {
  import util.RandomImplicits._

  override def imageIndex: Int = 13

  override def onLand(master: StandardGame.Master) = {
    master.contentArea.put(new MysteryContent(master.contentArea, count))
  }

  override def onDepart(master: StandardGame.Master) = {
    master.contentArea.clear()
  }

  override def onEmulate(master: StandardGame.Master) = {
    // These spaces get rarer later on, so it's fairly safe to emulate
    // them by assuming the player will get something (it will balance
    // out with the fruit spaces, whose emulation is ignored)
    val chosen = util.rand.nextOf(Fruits.apple, Fruits.orange, Fruits.melon)
    chosen.giveTo(master.player)
  }

}
