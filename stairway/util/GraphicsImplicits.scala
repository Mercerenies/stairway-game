
package com.mercerenies.stairway
package util

import java.awt.{Graphics2D, Image, FontMetrics}
import language.higherKinds
import collection.generic.CanBuildFrom
import collection.breakOut

object GraphicsImplicits {

  implicit class RichFontMetrics(val metrics: FontMetrics) extends AnyVal {

    def charWidth(): Int = {
      metrics.charWidth('m')
    }

    def charHeight(): Int = {
      metrics.getHeight
    }

  }

  implicit class RichGraphics(val graph: Graphics2D) extends AnyVal {

    def charWidth: Int =
      graph.getFontMetrics.charWidth()

    def charHeight: Int =
      graph.getFontMetrics.charHeight()

    def stringWidth(x: String): Int =
      graph.getFontMetrics.stringWidth(x)

    def stringHeight(x: String): Int =
      charHeight * x.split("\n").size

    def stringHeightWrapped(x: String, w: Int): Int =
      charHeight * stringLines[List](x, w).size

    // NOTE: Does not treat newlines specially; this only counts the soft word wraps
    def stringSoftLines[F[_]](
      x: String,
      w: Int)(
      implicit cbf: CanBuildFrom[Nothing, String, F[String]]
    ): F[String] = {
      val builder = cbf()
      var curr = ""
      for (word <- x.split(" ")) {
        val next = curr + word + " "
        if (stringWidth(next) >= w) {
          builder += curr
          curr = word + " "
        } else {
          curr = next
        }
      }
      if (curr != "")
        builder += curr
      builder.result()
    }

    def stringLines[F[A] <: Traversable[A]](
      x: String,
      w: Int)(
      implicit cbf: CanBuildFrom[Nothing, String, F[String]]
    ): F[String] = {
      (for {
        line <- x.split("\n").to[F]
        softLine <- stringSoftLines[F](line, w)
      } yield softLine)(breakOut)
    }

    def drawString(s: String, x: Double, y: Double) = {
      graph.drawString(s, x.toFloat, y.toFloat)
    }

    def drawStringLines(ss: Iterable[String], x: Int, y: Int) = {
      for ((s, i) <- ss.zipWithIndex) {
        val yPos = y + i * charHeight
        drawString(s, x, yPos)
      }
    }

    def drawStringWidth(s: String, x: Int, y: Int, w: Int) = {
      val lines = stringLines[List](s, w)
      drawStringLines(lines, x, y)
    }

    def drawImage(image: Image, rect: Rectangle) = {
      graph.drawImage(image, rect.x.toInt, rect.y.toInt, rect.width.toInt, rect.height.toInt, null)
    }

  }

}
