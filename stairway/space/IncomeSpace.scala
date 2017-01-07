
package com.mercerenies.stairway.space

import com.mercerenies.stairway.game.StandardGame

case object IncomeSpace extends ImageSpace {

  override def imageIndex: Int = 12

  override def onLand(master: StandardGame.Master) = {
    master.stats.money += master.stats.incomeAmount
  }

}
