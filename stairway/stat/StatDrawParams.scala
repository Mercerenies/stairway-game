
package com.mercerenies.stairway.stat

import java.awt.Color

case class StatDrawParams(
  width: Double,
  statPerLine: Double,
  color: Color) {}

object StatDrawParams {

  val LineHeight: Int = 10
  val LinePadding: Int = 5

  lazy val FullLineHeight: Int = LineHeight + LinePadding
  lazy val LineFraction: Double = LineHeight.toDouble / FullLineHeight.toDouble

}
