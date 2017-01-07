
package com.mercerenies.stairway.game.belt

import com.mercerenies.stairway.game.StandardGame
import com.mercerenies.stairway.space.Space
import com.mercerenies.stairway.util
import com.mercerenies.stairway.util.Index

class CyclicConveyer(master: StandardGame.Master, val spaces: Space*) extends ConveyerFeed(master) {

  override def getSpace(index: Index): Space = spaces(util.mod(index.value, spaces.size))

}
