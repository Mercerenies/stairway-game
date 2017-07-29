
package com.mercerenies.stairway.debug

import com.mercerenies.stairway.game._
import com.mercerenies.stairway.event.{StepEvent, ClickEvent}
import com.mercerenies.stairway.action.MouseClick
import java.awt._

class DebugBox(master: StandardGame.Master) extends GameEntity[StandardGame.Master](master) {

  def text: String = "ROOT: " + master.field.rootEnergy

  override def draw(graph: Graphics2D): Unit = {
    if ((DebugMode.enabled) && (text != "")) {
      graph.setColor(Color.black)
      graph.drawString(text, 10, 30)
    }
  }

}
