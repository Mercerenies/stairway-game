
package com.mercerenies.stairway.ui

import javax.swing.JFrame
import java.awt.Dimension
import java.awt.event.{MouseEvent, MouseAdapter, KeyEvent, KeyAdapter}
import com.mercerenies.stairway.game.EventDispatch
import com.mercerenies.stairway.action._

class UI(private val _events: EventDispatch) {

  private object MouseListener extends MouseAdapter {

    override def mousePressed(e: MouseEvent): Unit = {
      for (
        btn <- MouseButton.button(e)
      ) {
        _events += MouseClick(btn, ActionType.Down, (e.getX, e.getY))
      }
    }

    override def mouseReleased(e: MouseEvent): Unit = {
      for (
        btn <- MouseButton.button(e)
      ) {
        _events += MouseClick(btn, ActionType.Up, (e.getX, e.getY))
      }
    }

    override def mouseMoved(e: MouseEvent): Unit = {
      _events += MouseMove((e.getX, e.getY))
    }

    override def mouseDragged(e: MouseEvent): Unit = mouseMoved(e)

  }

  private object KeyListener extends KeyAdapter {

    override def keyPressed(e: KeyEvent): Unit = {
      _events += KeyAction(KeyboardKey(e.getKeyCode()), ActionType.Down)
    }

    override def keyReleased(e: KeyEvent): Unit = {
      _events += KeyAction(KeyboardKey(e.getKeyCode()), ActionType.Up)
    }

  }

  private val _frame = new Frame(_events)
  private var _tempDim = new Dimension

  def frame: JFrame = _frame

  def isClosed = _frame.isClosed

  def repaint() = {
    _frame.repaint()
  }

  def initialize() = {
    _frame.initialize()
    _frame.pane.addMouseListener(MouseListener)
    _frame.pane.addMouseMotionListener(MouseListener)
    _frame.pane.addKeyListener(KeyListener)
    _frame.pane.requestFocus()
  }

  def width = _frame.pane.getSize(_tempDim).width
  def height = _frame.pane.getSize(_tempDim).height

}
