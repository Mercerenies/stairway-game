
package com.mercerenies.stairway
package game

import event.StepEvent
import action.KeyboardKey
import util.IOFriendly
import java.io.{File, FileInputStream, FileOutputStream}
import java.awt.event.KeyEvent.{VK_F5, VK_F6}
import java.awt.{FileDialog, Graphics2D}

class SaveLoader(master: StandardGame.Master) extends GameEntity[StandardGame.Master](master) {

  private object FunctionKeys extends StepEvent {

    private lazy val f5 = KeyboardKey(VK_F5)
    private lazy val f6 = KeyboardKey(VK_F6)

    private var _justPressed5 = false
    private var _justPressed6 = false

    override def call(): Unit = {
      if (master.state.isKeyDown(f5)) {
        _justPressed5 = true
      } else if (_justPressed5) {
        val fd = new FileDialog(master.gameLoop.frame, "Save file", FileDialog.SAVE)
        fd.setVisible(true)
        (Option(fd.getDirectory()), Option(fd.getFile())) match {
          case (Some(dir), Some(filename)) =>
            val file = new FileOutputStream(new File(dir, filename))
            // TODO Protect the file.close()
            IOFriendly.write(master.mirror, new IOFriendly.Writer(file))
            file.close()
          case _ => {}
        }
        _justPressed5 = false
      } else if (master.state.isKeyDown(f6)) {
        _justPressed6 = true
      } else if (_justPressed6) {
        val fd = new FileDialog(master.gameLoop.frame, "Open file", FileDialog.LOAD)
        fd.setVisible(true)
        (Option(fd.getDirectory()), Option(fd.getFile())) match {
          case (Some(dir), Some(filename)) =>
            val file = new FileInputStream(new File(dir, filename))
            // TODO Protect the file.close()
            master.unmirror(IOFriendly.read[GameData](new IOFriendly.Reader(file)))
            file.close()
          case _ => {}
        }
        _justPressed6 = false
      }
    }

  }

  override def draw(graph: Graphics2D): Unit = {}

  stepEvent += FunctionKeys

}
