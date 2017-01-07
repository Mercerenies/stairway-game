
package com.mercerenies.stairway.util

object PointImplicits {

  implicit class Point(val xy: (Double, Double)) extends AnyVal {
    def x = xy._1
    def y = xy._2
    def within(rect: Rectangle): Boolean =
      x >= rect.xmin && x < rect.xmax && y >= rect.ymin && y < rect.ymax
  }

  implicit class IntPoint(val xy: (Int, Int)) extends AnyVal {
    def x = xy._1
    def y = xy._2
    def within(rect: Rectangle): Boolean = (x.toDouble, y.toDouble) within rect
  }

}
