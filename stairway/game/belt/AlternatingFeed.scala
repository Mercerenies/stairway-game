
package com.mercerenies.stairway.game.belt

import com.mercerenies.stairway.util.Index
import com.mercerenies.stairway.game.StandardGame
import com.mercerenies.stairway.space.Space
import scala.collection.mutable.Map
import scala.collection.concurrent.TrieMap
import scala.util.control.{Exception => UtilException}

class AlternatingFeed[+A <: ConveyerFeed, +B <: ConveyerFeed](
  master: StandardGame.Master,
  val feed1: A,
  val feed2: B)
    extends ConveyerFeed(master) {

  private val cache: Map[Index.Type, Space] = new TrieMap
  private var on2 = false

  def feed: ConveyerFeed = if (on2) feed2 else feed1

  def topDefined: Index.Type = {
    val zero = implicitly[Integral[Index.Type]].fromInt(0)
    UtilException.failAsValue(classOf[UnsupportedOperationException])(zero) {
      cache.keySet.max
    }
  }

  def switchTo(arg: AlternatingFeed.Alternate): Unit = arg match {
    case AlternatingFeed.One => { on2 = false }
    case AlternatingFeed.Two => { on2 = true }
  }

  override def bottomIndex = math.min(feed1.bottomIndex, feed2.bottomIndex)

  override def indexChanged(): Unit = {
    cache.retain { case (k, _) => k >= bottomIndex }
  }

  override def addIndex(n: Int) = {
    feed1.addIndex(n)
    feed2.addIndex(n)
    indexChanged()
  }

  override def getSpace(index: Index): Space = getCached(index) match {
    case None => {
      val space = feed.getSpace(index)
      cache(index.value) = space
      space
    }
    case Some(x) => x
  }

  def getCached(index: Index): Option[Space] = cache.get(index.value)

}

object AlternatingFeed {

  sealed abstract class Alternate
  case object One extends Alternate
  case object Two extends Alternate

}
