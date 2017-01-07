
package com.mercerenies.stairway.util

import scala.language.implicitConversions
import java.awt.geom.Rectangle2D

case class Rectangle(x0: Double, y0: Double, x1: Double, y1: Double) {

  def x = Math.min(x0, x1)
  def y = Math.min(y0, y1)
  def width = Math.abs(x1 - x0)
  def height = Math.abs(y1 - y0)

  def xmin = x
  def ymin = y
  def xmax = x + width
  def ymax = y + height

  def centerX = (xmax + xmin) / 2
  def centerY = (ymax + ymin) / 2

  def shift(dx: Double, dy: Double) = Rectangle(x0 + dx, y0 + dy, x1 + dx, y1 + dy)

  def jrect: Rectangle2D = new Rectangle2D.Double(x, y, width, height)

}

object Rectangle {

  implicit def rectangle2jrectangle(rect: Rectangle): Rectangle2D = rect.jrect

}
