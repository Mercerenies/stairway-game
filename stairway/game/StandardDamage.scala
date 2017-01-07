
package com.mercerenies.stairway.game

import com.mercerenies.stairway.util.{Rectangle, toward}
import com.mercerenies.stairway.event.StepEvent
import java.awt.{Color, Graphics2D}

class StandardDamage(master: StandardGame.Master, rect: Rectangle)
    extends DamageFunctions(master, rect) {

  object StepTransition extends StepEvent {
    override def call(): Unit = {
      _displayShift = toward(_displayShift, _shift, 0.5)
    }
  }

  object StepStabilizer extends StepEvent {
    override def call(): Unit = {
      if (_displayShift == _shift && _shift > period) {
        _shift -= period
        _displayShift -= period
      }
    }
  }

  private var _shift = -20.0
  private var _displayShift = -20.0

  override lazy val functions: Seq[DamageFunctions.Function] = List(
    DamageFunctions.Function((_: Double) => 0.5, Color.red),
    DamageFunctions.Function(displayFunction _, Color.blue)
  )

  val periodDivisor: Double = 30
  val period: Double = (2 * math.Pi) * periodDivisor

  def actionStep: Double = 5.0
  def actionPosition: Double = 20.0

  def horizontalShift: Double = _shift

  def advance(): Unit = {
    advance(actionStep)
  }

  def advance(amount: Double): Unit = {
    _shift += amount
  }

  def displayFunction(x: Double): Double =
    math.sin((x + _displayShift) / periodDivisor) / 2 + 0.5

  def magicFunction(x: Double): Double =
    math.sin((x + horizontalShift) / periodDivisor) / 2 + 0.5

  def magicValue: Double = magicFunction(actionPosition)

  override def draw(graph: Graphics2D): Unit = {
    super.draw(graph)
    graph.setColor(Color.black)
    graph.drawLine(
      (rect.xmin + actionPosition).toInt,
      rect.ymin.toInt,
      (rect.xmin + actionPosition).toInt,
      rect.ymax.toInt
    )
  }

  stepEvent += StepTransition
  stepEvent += StepStabilizer

}
