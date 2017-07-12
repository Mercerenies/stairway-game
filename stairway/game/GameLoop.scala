
package com.mercerenies.stairway.game

import scala.util.control.{Exception => UtilException}
import java.awt.Graphics2D
import javax.swing.JFrame
import com.mercerenies.stairway.ui.UI
import com.mercerenies.stairway.util.ThreadSafeQueue
import com.mercerenies.stairway.action.Action

class GameLoop(val master: GameMaster) extends Runnable {

  private val _actions = new ThreadSafeQueue[Action]
  private var _thread: Option[Thread] = None
  private val ui: UI = new UI(new EventDispatch(this))
  val state = new GameState

  def start(): Unit = {
    if (_thread.isEmpty) {
      ui.initialize()
      val thread = new Thread(this)
      _thread = Some(thread)
      thread.start()
    }
  }

  def kill(): Unit = {
    _thread = None
  }

  def frame: JFrame = ui.frame

  def roomSize = (ui.width, ui.height)
  def roomWidth = roomSize._1
  def roomHeight = roomSize._2

  protected def cleanup(): Unit = {}

  protected def step() = master.step()
  protected def draw() = ui.repaint()
  def drawEntities(graph: Graphics2D) = master.draw(graph)

  protected def events() = {
    _actions.popAll().foreach(_.react(this))
  }

  override def run(): Unit = {
    while (!_thread.isEmpty) {
      val startTime = System.nanoTime()
      // Handle events
      events()
      // Update the game
      step()
      // Draw to the screen
      draw()
      // Wait till the end of the frame
      val endTime = startTime + GameLoop.timePerFrame
      while (System.nanoTime() < endTime) {
        UtilException.ignoring(classOf[InterruptedException]) {
          Thread.sleep(1)
        }
      }
      // Check whether the UI is still alive
      if (ui.isClosed)
        kill()
    }
    cleanup()
  }

  def pushAction(action: Action) = _actions += action

}

object GameLoop {

  val FPS = 60.0
  val NanoPerSec = 1.0e9

  def timePerFrame = NanoPerSec / FPS

}
