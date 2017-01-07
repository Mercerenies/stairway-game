
package com.mercerenies.stairway.game.world

import com.mercerenies.stairway.game.belt.ConveyerFeed
import com.mercerenies.stairway.util.Index
import com.mercerenies.stairway.space.EnemySpace
import com.mercerenies.stairway.enemy.{Enemy, EnemyBox}
import scala.util.Random

trait EnemySequenceGenerator[+T <: GeneratorFeed] extends Generator[T] {

  def nextEnemy(): (Enemy, Int)

  abstract override def trigger(index: Index): Unit = {
    val (e, n) = nextEnemy()
    val box = new EnemyBox(e)
    for (i <- 0 until n) {
      feed.assign(index + i, EnemySpace(box))
    }
    super.trigger(index)
  }

}
