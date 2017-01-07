
package com.mercerenies.stairway.action

sealed trait ActionType {
  def toBoolean: Boolean
}

object ActionType {
  case object Up extends ActionType {
    override def toBoolean = false
  }
  case object Down extends ActionType {
    override def toBoolean = true
  }
}
