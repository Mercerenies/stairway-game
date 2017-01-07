
package com.mercerenies.stairway.game

import com.mercerenies.stairway.image.ButtonsImage
import com.mercerenies.stairway.event.StepEvent
import com.mercerenies.stairway.action.KeyboardKey
import com.mercerenies.stairway.game.button._
import scala.collection.immutable.Vector
import java.awt.{Graphics2D, Font, Color}
import java.awt.event.KeyEvent.VK_E

class ButtonPad(master: StandardGame.Master, val upperLeft: (Int, Int))
    extends GameEntity[StandardGame.Master](master) {

  val imageResource = new ButtonsImage

  val moveButton = new MoveButton(this)
  val fadeButton = new FadeButton(this)
  val extraButton = new Button(this, 2, List(KeyboardKey(VK_E)))
  val physicalAtkButton = new PhysicalButton(this)
  val magicalAtkButton = new MagicalButton(this)
  val specialAtkButton = new SpecialButton(this)
  val appleButton = new AppleButton(this)
  val orangeButton = new OrangeButton(this)
  val melonButton = new MelonButton(this)

  val buttons: Vector[Button] = Vector(
    moveButton, fadeButton, extraButton,
    physicalAtkButton, magicalAtkButton, specialAtkButton,
    appleButton, orangeButton, melonButton
  )

  override def draw(graph: Graphics2D): Unit = {
    buttons.foreach((button: Button) => button.draw(graph, button.bbox))
  }

  buttons.foreach(stepEvent += _)

}
