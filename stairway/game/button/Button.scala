
package com.mercerenies.stairway
package game.button

import game.ButtonPad
import game.tagline.Tagged
import image.ButtonsImage
import event.StepEvent
import action.KeyboardKey
import ui.Drawable
import util.Rectangle
import java.awt.Graphics2D

abstract class Button(
  val pad: ButtonPad,
  val bbox: Rectangle,
  val imageIndex: Int,
  val hotkeys: Seq[KeyboardKey] = Nil)
    extends Drawable
    with StepEvent
    with Tagged {
  import util.GraphicsImplicits._
  import util.PointImplicits._

  private var lastPressState = false
  private var _justPressed = false
  private var _justReleased = false

  def this(pad: ButtonPad, index: Int) =
    this(pad, Button.rect(pad.upperLeft, index), index)

  def this(pad: ButtonPad, index: Int, hotkeys: Seq[KeyboardKey]) =
    this(pad, Button.rect(pad.upperLeft, index), index, hotkeys)

  def this(pad: ButtonPad, index: Int, hotkey: KeyboardKey) =
    this(pad, Button.rect(pad.upperLeft, index), index, List(hotkey))

  def master = pad.master

  def hotkeyPressed: Boolean = hotkeys.exists(h => master.state.isKeyDown(h))

  def isPressed: Boolean =
    (master.state.isLeftMouseDown && (master.state.mousePosition within bbox)) ||
    hotkeyPressed

  def justPressed = _justPressed
  def justReleased = _justReleased

  override def call(): Unit = {
    _justPressed  = (!lastPressState &&  isPressed)
    _justReleased = ( lastPressState && !isPressed)
    lastPressState = isPressed
  }

  override def tagText: Option[String] = Some(buttonDesc)

  def buttonDesc: String

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
    graph.drawImage(pad.imageResource.button(isPressed, imageIndex), rect)
  }

}

object Button {
  def pos(upperLeft: (Int, Int), i: Int): (Int, Int) = {
    val col = i % 3
    val row = i / 3
    (upperLeft._1 + col * ButtonsImage.Width, upperLeft._2 + row * ButtonsImage.Height)
  }
  def rect(upperLeft: (Int, Int), i: Int): Rectangle = {
    val(xPos, yPos) = pos(upperLeft, i)
    Rectangle(xPos, yPos, xPos + ButtonsImage.Width, yPos + ButtonsImage.Height)
  }
}
