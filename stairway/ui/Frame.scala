
package com.mercerenies.stairway.ui

import javax.swing._
import java.awt._
import java.awt.event.{WindowAdapter, WindowEvent}
import com.mercerenies.stairway.game.EventDispatch

class Frame(private val _events: EventDispatch) extends JFrame("Stairway") {

  val pane = new Panel(_events)
  private var _closed = false

  private object Listener extends WindowAdapter {
    override def windowClosing(e: WindowEvent) = {
      _closed = true
    }
  }

  def initialize() = {
    add(pane)
    pack()
    setResizable(false)
    setLocationRelativeTo(null)
    setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE)
    setVisible(true)
  }

  def isClosed = _closed

  addWindowListener(Listener)

}
