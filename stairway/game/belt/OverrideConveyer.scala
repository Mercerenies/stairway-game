
package com.mercerenies.stairway.game.belt

import com.mercerenies.stairway.util.Index
import com.mercerenies.stairway.game.StandardGame
import com.mercerenies.stairway.space.Space
import scala.collection.mutable.Map
import scala.collection.concurrent.TrieMap

class OverrideConveyer[+T <: ConveyerFeed](master: StandardGame.Master, val feed: T)
    extends ConveyerFeed(master) {

  private val overrides: Map[Index.Type, Space] = new TrieMap

  override def getSpace(index: Index): Space = overrides.get(index.value) match {
    case None => feed.getSpace(index)
    case Some(x) => x
  }

  override def bottomIndex = feed.bottomIndex

  override def putIndex(n: Int) = {
    feed.putIndex(n)
    indexChanged()
  }

  override def indexChanged(): Unit = {
    overrides.retain { case (k, _) => k >= bottomIndex }
  }

  override def addIndex(n: Int) = {
    feed.addIndex(n)
    indexChanged()
  }

  def assignOverride(n: Index, space: Space): Unit = {
    overrides(n.value) = space
  }

  def deleteOverride(n: Index): Unit = {
    overrides -= n.value
  }

}
