
package com.mercerenies.stairway.game.belt

import com.mercerenies.stairway.game.StandardGame
import com.mercerenies.stairway.space.{Space, TextualSpace}
import com.mercerenies.stairway.util.Index

class BasicConveyer(master: StandardGame.Master) extends ConveyerFeed(master) {

  override def getSpace(index: Index): Space = TextualSpace(index.value.toString)

}
