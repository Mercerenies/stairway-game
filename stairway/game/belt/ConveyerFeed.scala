
package com.mercerenies.stairway.game.belt

import com.mercerenies.stairway.game.{StandardGame, Player}
import com.mercerenies.stairway.util.{Index, Rectangle}
import com.mercerenies.stairway.space.Space
import java.awt._

abstract class ConveyerFeed(val master: StandardGame.Master) {

  private var _bottomIndex: Index.Type = - Player.DefaultOccupiedSpace

  def getSpace(index: Index): Space

  def bottomIndex = _bottomIndex

  protected def indexChanged() = {}

  def addIndex(n: Int) = {
    _bottomIndex += Math.abs(n)
    indexChanged()
  }

  def putIndex(n: Int) = {
    _bottomIndex = Math.abs(n)
    indexChanged()
  }

}

