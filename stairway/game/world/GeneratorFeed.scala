
package com.mercerenies.stairway.game.world

import com.mercerenies.stairway.util.Index
import com.mercerenies.stairway.game.StandardGame
import com.mercerenies.stairway.game.belt._
import com.mercerenies.stairway.space.{Space, NeutralSpace}
import scala.collection.mutable.{Map, Queue}
import scala.collection.concurrent.TrieMap
import scala.util.control.{Exception => UtilException}
import scala.util.{Try, Success, Failure}

abstract class GeneratorFeed(
  master: StandardGame.Master)
    extends ConveyerFeed(master) {

  private val cache: Map[Index.Type, Space] = new TrieMap
  private val queue: Queue[Generator[GeneratorFeed]] = new Queue
  private var frozen: Boolean = true

  def generators: Seq[Generator[GeneratorFeed]]

  def default(index: Index): Space = NeutralSpace

  def topDefined: Index.Type = {
    val zero = implicitly[Integral[Index.Type]].fromInt(0)
    UtilException.failAsValue(classOf[UnsupportedOperationException])(zero) {
      cache.keySet.max
    }
  }

  override protected def indexChanged(): Unit = {
    cache.retain { case (k, _) => k >= bottomIndex }
  }

  private def advanceSteps(index: Index)(implicit int: Integral[Index.Type]): Unit = {
    import Integral.Implicits._

    // The GeneratorFeed will freeze during a boss fight so it doesn't waste time generating spaces that
    // won't be used.
    if (frozen) {
      frozen = false
    } else {
      val top = topDefined
      val steps = index.value - top
      for (i <- 0 until steps.toInt) {
        generators.foreach { gen =>
          gen.forward(Index.Absolute(top + i))
          if (gen.ready && !queue.contains(gen))
            queue += gen
        }
      }
    }

  }

  private def computeSpace(index: Index): Space = {
    def trySpace(): Space = Try(queue.dequeue()) match {
      case Failure(_) => {
        val def_ = default(index)
        assign(index, def_)
        def_
      }
      case Success(x) => {
        x.trigger(index)
        cache.get(index.value) match {
          case None => trySpace()
          case Some(x) => x
        }
      }
    }
    advanceSteps(index)
    trySpace()
  }

  override def getSpace(index: Index): Space = cache.get(index.value) match {
    case None => computeSpace(index)
    case Some(x) => x
  }

  def freeze(): Unit = {
    // freeze() freezes the feed. The feed automatically unfreezes whenever getSpace() is called again with
    // an unknown argument.
    frozen = true
  }

  def assign(index: Index, space: Space): Unit = {
    cache(index.value) = space
  }

  def eraChanged(newEra: Int): Unit = {
    generators.foreach(_.eraChanged(newEra))
  }

}

