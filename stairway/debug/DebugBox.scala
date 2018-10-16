
package com.mercerenies.stairway.debug

import com.mercerenies.stairway.game._
import com.mercerenies.stairway.event.{StepEvent, ClickEvent}
import com.mercerenies.stairway.action.MouseClick
import java.awt._

/** A box for displaying debugging text.
  *
  * This relatively simple box occupies the upper-left corner of the
  * screen and is used for printing simple debug strings. This box
  * will always be empty in production.
  *
  * @constructor
  * @param master the master game object
  */
class DebugBox(master: StandardGame.Master) extends GameEntity[StandardGame.Master](master) {

  //def text: String = "ROOT: " + master.field.rootEnergy
  //def text: String = "KARMA: " + master.luck.karma
  def text = ""

  override def draw(graph: Graphics2D): Unit = {
    if ((DebugMode.enabled) && (text != "")) {
      graph.setColor(Color.black)
      graph.drawString(text, 10, 30)
    }
  }

}
