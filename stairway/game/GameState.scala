
package com.mercerenies.stairway.game

import com.mercerenies.stairway.action.{MouseButton, KeyboardKey}
import scala.collection.mutable.Map

class GameState {

  // In the future, we might separate this into two completely separate objects so that only select
  // parts of the system have write access to this. Making this an inner object now will make it
  // easier to track down the mutator portions of the code later on.
  object Writer {
    def leftMouse = _leftMouse
    def middleMouse = _middleMouse
    def rightMouse = _rightMouse
    def mousePos = _mousePos
    def leftMouse_=(x: Boolean) = {
      _leftMouse = x
    }
    def middleMouse_=(x: Boolean) = {
      _middleMouse = x
    }
    def rightMouse_=(x: Boolean) = {
      _rightMouse = x
    }
    def mousePos_=(x: (Int, Int)) = {
      _mousePos = x
    }
    def keyPressed(key: KeyboardKey) = {
      _keyMap(key) = true
    }
    def keyReleased(key: KeyboardKey) = {
      _keyMap(key) = false
    }
  }

  private var _leftMouse = false
  private var _middleMouse = false
  private var _rightMouse = false
  private var _mousePos = (0, 0)
  private val _keyMap: Map[KeyboardKey, Boolean] = Map()

  def isLeftMouseDown = _leftMouse
  def isMiddleMouseDown = _middleMouse
  def isRightMouseDown = _rightMouse
  def isButtonDown(button: MouseButton): Boolean = button match {
    case MouseButton.Left => isLeftMouseDown
    case MouseButton.Middle => isMiddleMouseDown
    case MouseButton.Right => isRightMouseDown
  }
  def mousePosition: (Int, Int) = _mousePos
  def isKeyDown(key: KeyboardKey) = _keyMap.applyOrElse(key, (_: KeyboardKey) => false)
  def isKeyUp(key: KeyboardKey) = !isKeyDown(key)

}
