
package com.mercerenies.stairway.luck

import com.mercerenies.stairway.util

object DiceWaterfall {
  import util.RandomImplicits._

  def getValues(count: Int, total: DiceValue): List[DiceValue] =
    util.rand.nextOf((for (lst <- DiceNumbers(count).enumerate; if lst.sum == total) yield lst): _*)

}
