
package com.mercerenies.stairway.space

import com.mercerenies.stairway.game.StandardGame

case object IncomeSpace extends ImageSpace {

  override def imageIndex: Int = 12

  override def onLand(master: StandardGame.Master) = {
    master.stats.money += master.stats.incomeAmount
  }

  override def name = "Income Space"

  override def desc = "Gain a small amount of money"

}
