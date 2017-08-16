
package com.mercerenies.stairway.space

import com.mercerenies.stairway.game.StandardGame

case object TaxSpace extends ImageSpace {

  override def imageIndex: Int = 2

  override def onLand(master: StandardGame.Master) = {
    master.stats.deductTax()
  }

  override def onEmulate(master: StandardGame.Master) = {
    master.stats.deductTax(master.stats.taxPercent / 4)
  }

  override def name = "Tax Space"

  override def desc = "Taxes are due; pay some percent of your money if you land here"

}
