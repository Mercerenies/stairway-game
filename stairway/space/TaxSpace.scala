
package com.mercerenies.stairway.space

import com.mercerenies.stairway.game.StandardGame

case object TaxSpace extends ImageSpace {

  override def imageIndex: Int = 2

  override def onLand(master: StandardGame.Master) = {
    master.stats.deductTax()
  }

}
