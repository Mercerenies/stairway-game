
package com.mercerenies.stairway
package stat

import java.awt.{Graphics, Graphics2D, Color}
import util.Rectangle
import ui.Drawable

class BarStat(
  startValue: Double,
  private var _maxv: Double,
  val drawArgs: StatDrawParams,
  val onChange: (Double) => Unit,
  val onChangeMax: (Double) => Unit) {

  object Draw extends Drawable {

    private val speed: Double = 2.5

    private var dValue = startValue
    private var dMax = _maxv

    override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
      import StatDrawParams._
      val statPerLine = drawArgs.statPerLine
      val perLine = rect.height / lines
      val contentSpace = perLine * LineFraction
      for (i <- 0 until lines) {
        val y = rect.y + i * perLine
        val valFrac = util.clamp(dValue - statPerLine * i, 0, statPerLine) / statPerLine
        val maxFrac = util.clamp(dMax - statPerLine * i, 0, statPerLine) / statPerLine
        val valPos = util.lerp(rect.xmin, rect.xmax, valFrac)
        val maxPos = util.lerp(rect.xmin, rect.xmax, maxFrac)
        graph.setColor(Color.black)
        graph.fill(Rectangle(rect.xmin, y, maxPos, y + contentSpace))
        graph.setColor(drawArgs.color)
        graph.fill(Rectangle(rect.xmin, y, valPos, y + contentSpace))
      }
    }

    // This is the "desired" width and height for draw(). It is not a requirement, and draw()
    // will work with any rectangle it is given.
    def dimens: (Double, Double) = (width, height)
    def lines: Int = (dMax / drawArgs.statPerLine).ceil.toInt
    def width: Double = drawArgs.width
    def height: Double = lines * StatDrawParams.FullLineHeight

    def refresh() = {
      if (dValue != value.toDouble)
        dValue = util.toward(dValue, value.toDouble, speed)
      if (dMax != max.toDouble)
        dMax = util.toward(dMax, max.toDouble, speed)
    }

  }
  Draw // Force instantiation

  class Assigner(private val field: () => Double, private val setter: (Double) => Double) {
    def toDouble: Double = field()
    def +=(x: Double): Double = {
      setter(toDouble + x)
    }
    def -=(x: Double): Double = {
      setter(toDouble - x)
    }
  }

  private var fromTop: Double = {
    val attempt = _maxv - startValue
    util.clamp(attempt, 0, _maxv)
  }

  private val _value: Assigner = new Assigner(() => _maxv - fromTop, value_=(_))

  private val _max: Assigner = new Assigner(() => _maxv, max_=(_))

  def this(startValue: Double, maxValue: Double, drawArgs: StatDrawParams) =
    this(startValue, maxValue, drawArgs, (_: Double) => {}, (_: Double) => {})

  def value: Assigner = _value
  def max: Assigner = _max

  def value_=(x: Double): Double = {
    val prior = fromTop
    val attempt = _maxv - x
    fromTop = util.clamp(attempt, 0, _maxv)
    if (prior - fromTop != 0)
      onChange(prior - fromTop)
    _maxv - fromTop
  }

  def max_=(x: Double): Double = {
    val prior = _maxv
    _maxv = x
    if (_maxv < 0)
      _maxv = 0
    if (prior - _maxv != 0)
      onChangeMax(_maxv - prior)
    value += 0 // Reassert that the value is still valid
    _maxv
  }

  def refresh() = Draw.refresh()

}
