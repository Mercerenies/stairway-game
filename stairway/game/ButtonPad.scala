
package com.mercerenies.stairway
package game

import image.ButtonsImage
import event.StepEvent
import action.KeyboardKey
import game.button._
import debug.EmulateButton
import util.PointImplicits._
import scala.collection.immutable.Vector
import java.awt.{Graphics2D, Font, Color}

class ButtonPad(master: StandardGame.Master, val upperLeft: (Int, Int))
    extends GameEntity[StandardGame.Master](master) {

  val imageResource = new ButtonsImage

  val moveButton = new MoveButton(this)
  val fadeButton = new FadeButton(this)
  val emulateButton = new EmulateButton(this)
  val physicalAtkButton = new PhysicalButton(this)
  val magicalAtkButton = new MagicalButton(this)
  val specialAtkButton = new SpecialButton(this)
  val appleButton = new AppleButton(this)
  val orangeButton = new OrangeButton(this)
  val melonButton = new MelonButton(this)

  val buttons: Vector[Button] = Vector(
    moveButton, fadeButton, emulateButton,
    physicalAtkButton, magicalAtkButton, specialAtkButton,
    appleButton, orangeButton, melonButton
  )

  def mouseOverButton: Option[Button] =
    buttons find { b => master.state.mousePosition within b.bbox }

  override def draw(graph: Graphics2D): Unit = {
    buttons.foreach((button: Button) => button.draw(graph, button.bbox))
  }

  buttons.foreach(stepEvent += _)

}
