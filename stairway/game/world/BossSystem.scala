
package com.mercerenies.stairway.game.world

import com.mercerenies.stairway.game.StandardGame
import com.mercerenies.stairway.game.belt.{ConveyerFeed, AlternatingFeed}
import com.mercerenies.stairway.util.Index
import com.mercerenies.stairway.space.{Space, NeutralSpace, EnemySpace}
import com.mercerenies.stairway.enemy.{Enemy, BossEnemy, EnemyBox}

abstract class BossSystem[+A <: GeneratorFeed](
  master: StandardGame.Master,
  val underlyingFeed: A) {

  private val iterator: Iterator[EnemyBox[BossEnemy]] = bosses.map(new EnemyBox(_)).iterator
  private var curr: Option[EnemyBox[BossEnemy]] = None

  val altFeed = AltFeed

  def bosses: Seq[BossEnemy]

  def timeToSwitch(index: Index): Boolean

  def regularFeed = AlternatingFeed.One
  def bossFeed = AlternatingFeed.Two

  def onSwitch(arg: AlternatingFeed.Alternate): Unit = {}

  object AltFeed extends AlternatingFeed[RegularFeed.type, BossFeed.type](master, RegularFeed, BossFeed) {

    override def switchTo(arg: AlternatingFeed.Alternate): Unit = {
      super.switchTo(arg)
      onSwitch(arg)
      if (arg == bossFeed)
        underlyingFeed.freeze()
    }

  }

  object RegularFeed extends ConveyerFeed(master) {

    override def getSpace(index: Index): Space = {
      val space = underlyingFeed.getSpace(index)
      if (timeToSwitch(index)) {
        if (iterator.hasNext) {
          curr = Some(iterator.next)
          altFeed.switchTo(bossFeed)
        } else {
          curr = None
        }
      }
      space
    }

  }

  object BossFeed extends ConveyerFeed(master) {

    override def getSpace(index: Index): Space = curr match {
      case None => {
        altFeed.switchTo(regularFeed) // No bosses, so switch back
        NeutralSpace
      }
      case Some(x) if x.hasBeenKilled => {
        altFeed.switchTo(regularFeed) // Dead boss, so switch back
        EnemySpace(x)
      }
      case Some(x) => EnemySpace(x) // We have a boss, so use it
    }

  }

}
