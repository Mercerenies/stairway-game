
package com.mercerenies.stairway.game

import com.mercerenies.stairway.util
import com.mercerenies.stairway.event.StepEvent
import scala.collection.mutable.{Set, HashSet}
import java.awt.{Graphics2D, Color, Font}

class ParticleText(master: StandardGame.Master, val font: Font = ParticleText.DefaultFont)
    extends GameEntity[StandardGame.Master](master) {

  import util.RandomImplicits._

  private class Particle(
    val text: String,
    val color: Color,
    private var life: Int,
    xy: (Double, Double),
    dxy: (Double, Double) =
      util.toVector(util.rand.nextDouble(0.02, 1.00), util.rand.nextDouble(0.0, 360.0))) {

    var xPos = xy._1
    var yPos = xy._2
    val dxPos = dxy._1
    val dyPos = dxy._2

    def stepPart(): Unit = {
      life -= 1
      if (life < 0)
        particles remove this
      xPos += dxPos
      yPos += dyPos
    }

    def drawPart(graph: Graphics2D): Unit = {
      graph.setColor(color)
      val metrics = graph.getFontMetrics()
      val width = metrics.stringWidth(text)
      val height = metrics.getHeight()
      graph.drawString(text, (xPos - width / 2).toFloat, (yPos + height / 2).toFloat)
    }

  }

  private object Step extends StepEvent {

    override def call(): Unit = {
      particles.foreach(_.stepPart())
    }

  }

  private val particles: Set[Particle] = new HashSet

  override def draw(graph: Graphics2D): Unit = {
    graph.setFont(font)
    particles.foreach(_.drawPart(graph))
  }

  def addParticle(
    text: String,
    color: Color,
    bounds: util.Rectangle,
    dir: (Double, Double) = (0.0, 360.0),
    life: Int = 120
  ): Unit = {
    val xPos = util.rand.nextDouble(bounds.xmin, bounds.xmax)
    val yPos = util.rand.nextDouble(bounds.ymin, bounds.ymax)
    val (dxPos, dyPos) = util.toVector(
      util.rand.nextDouble(0.02, 1.00),
      util.rand.nextDouble(dir._1, dir._2)
    )
    particles += new Particle(text, color, life, (xPos, yPos))
  }

  stepEvent += Step

}

object ParticleText {

  lazy val DefaultFont = new Font(Font.SANS_SERIF, Font.BOLD, 20)

}
