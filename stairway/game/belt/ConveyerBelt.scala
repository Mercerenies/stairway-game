
package com.mercerenies.stairway
package game.belt

import game.{GameEntity, StandardGame}
import util.{Index, Rectangle}
import util.PointImplicits._
import space.Space
import event.AbstractStepEvent
import java.awt._

class ConveyerBelt[+T <: ConveyerFeed](
  _master: StandardGame.Master,
  val feed: T,
  val rightMargin: Double)
    extends GameEntity[StandardGame.Master](_master) {

  case class RelativeIndex(private val _value: Index.Type) extends Index {
    override def value = bottomIndex + _value
  }

  private class MoveUpStep(val distance: Double, val speed: Double, private val onReached: () => Unit)
      extends AbstractStepEvent(ConveyerBelt.this) {
    private var traveled: Double = 0
    override def call() = {
      shiftBottom(speed)
      traveled += speed
      if (traveled >= distance) {
        stepActions -= 1
        onReached()
        finish()
      }
    }
  }

  private var stepActions = 0

  private var _bottomPosition: Double = ConveyerBelt.SpaceDim.height / 2

  def getSpace(index: Index) = feed.getSpace(index)

  def bottomPosition = _bottomPosition
  def bottomIndex = feed.bottomIndex

  def putIndex(n: Int) = {
    feed.putIndex(n)
  }

  def shiftBottom(n: Double) = {
    _bottomPosition += n
    updatePosition()
  }

  def updatePosition(): Unit = {
    val height = ConveyerBelt.SpaceDim.height
    if (bottomPosition > height) {
      feed.addIndex(1)
      shiftBottom(- height)
    }
  }

  def isMoving: Boolean = stepActions != 0

  def animatedShift(spaces: Int)(onReached: => Unit): Unit = {
    stepActions += 1
    stepEvent += new MoveUpStep(ConveyerBelt.SpaceDim.height * spaces, 2, () => onReached)
  }

  override def draw(graph: Graphics2D): Unit = {
    val lhsX = (rightMargin - ConveyerBelt.SpaceDim.width) / 2
    val lhsY = master.roomHeight + _bottomPosition
    var rect = Rectangle(lhsX, lhsY, lhsX + ConveyerBelt.SpaceDim.width, lhsY + ConveyerBelt.SpaceDim.height)
    var counter = 0
    while (rect.ymax > 0) {
      rect = rect.shift(0, - ConveyerBelt.SpaceDim.height)
      val space = getSpace(RelativeIndex(counter))
      space.draw(graph, rect)
      counter += 1
    }
  }

  def positionOf(index: Index): Rectangle = {
    val lhsX = (rightMargin - ConveyerBelt.SpaceDim.width) / 2
    val lhsY = master.roomHeight + _bottomPosition
    val base = Rectangle(lhsX, lhsY, lhsX + ConveyerBelt.SpaceDim.width, lhsY + ConveyerBelt.SpaceDim.height)
    base.shift(0, - ConveyerBelt.SpaceDim.height * (index - RelativeIndex(0) + 1))
  }

  def mouseOver: Option[Index] =
    Stream.
      from(0).
      map { RelativeIndex(_) }.
      map { x => (x, positionOf(x)) }.
      takeWhile { _._2.ymax > 0 }.
      find { master.state.mousePosition within _._2 }.
      map { _._1 }

}

object ConveyerBelt {
  val SpaceDim = new Dimension(100, 48)
}
