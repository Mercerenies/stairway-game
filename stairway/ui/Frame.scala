
package com.mercerenies.stairway.ui

import javax.swing._
import java.awt._
import java.awt.event.{WindowAdapter, WindowEvent}
import com.mercerenies.stairway.game.EventDispatch

/** The frame containing the game's interface.
  *
  * @constructor
  * @param _events the object to use for dispatching of events
  */
class Frame(private val _events: EventDispatch) extends JFrame("Stairway") {

  /** The central panel containing the game contents.
    */
  val pane = new Panel(_events)
  private var _closed = false

  private object Listener extends WindowAdapter {
    override def windowClosing(e: WindowEvent) = {
      _closed = true
    }
  }

  /** Initializes and displays the game window.
    */
  def initialize() = {
    add(pane)
    pack()
    setResizable(false)
    setLocationRelativeTo(null)
    setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE)
    setVisible(true)
  }

  /** Returns whether the frame has been closed, either by the user or
    * the game.
    */
  def isClosed = _closed

  addWindowListener(Listener)

}
