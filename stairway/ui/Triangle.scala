
package com.mercerenies.stairway.ui

import java.awt.Polygon

class Triangle(x0: Double, y0: Double, x1: Double, y1: Double, x2: Double, y2: Double)
    extends Polygon(Array(x0.toInt, x1.toInt, x2.toInt), Array(y0.toInt, y1.toInt, y2.toInt), 3)
