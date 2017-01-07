
package com.mercerenies.stairway.game

import java.awt._
import com.mercerenies.stairway.event.{StepEvent, ClickEvent}
import com.mercerenies.stairway.action.MouseClick

class DebugBox(master: StandardGame.Master) extends GameEntity[StandardGame.Master](master) {

  def text: String = "KARMA: " + master.luck.karma

  override def draw(graph: Graphics2D): Unit = {
    if ((DebugBox.IsEnabled) && (text != "")) {
      graph.setColor(Color.black)
      graph.drawString(text, 10, 30)
    }
  }

}

object DebugBox {
  val IsEnabled = true
}
