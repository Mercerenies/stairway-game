
package com.mercerenies.stairway.luck

import com.mercerenies.stairway.util
import com.mercerenies.stairway.event.StepEvent
import com.mercerenies.stairway.game.content.Content
import scala.collection.mutable.ListBuffer
import java.awt.Graphics2D

trait DiceContainer {

  private val _dice = new ListBuffer[Die]

  def dice: Traversable[Die] = _dice

  def hasDice: Boolean = !dice.isEmpty

  def addDie(die: Die): Unit = {
    _dice += die
  }

  def removeDie(die: Die): Unit = {
    _dice -= die
  }

  def clearDice(): Unit = {
    _dice.clear()
  }

  def stepDice(): Unit = {
    dice.foreach(_.step())
  }

  def drawDice(graph: Graphics2D): Unit = {
    dice.foreach((die) => die.draw(graph, die.rect))
  }

  def spawnDie(value: DiceValue, pos: (Int, Int)): Die = {
    val die = new Die(this, value, pos._1, pos._2)
    this addDie die
    die
  }

  def diceValue: Option[DiceValue] =
    if (dice.exists(_.result.isEmpty))
      None
    else
      Some((for (die <- dice; m <- die.result) yield m).sum)

}

object DiceContainer {
  import util.RandomImplicits._

  implicit class DiceContainerWithContent[T <: DiceContainer with Content](val value: T) extends AnyVal {

    def spawnDie(n: DiceValue): Die = {
      val rect = value.contentArea.rect
      val xPos = util.lerp(rect.xmin, rect.xmax, util.rand.nextDouble(0.25, 0.75))
      val yPos = rect.ymax
      value.spawnDie(n, (xPos.toInt, yPos.toInt))
    }

  }

}
