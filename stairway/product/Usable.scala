
package com.mercerenies.stairway.product

import com.mercerenies.stairway.game.Player

trait Usable {

  def use(player: Player): Unit

}
