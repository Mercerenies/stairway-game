
package com.mercerenies.stairway.action

/** A simple sum type distinguishing "presses" from "releases".
  */
sealed trait ActionType {
  /** @return true if the action is down
    */
  def toBoolean: Boolean
}

/** Contains the values of the sealed trait [[ActionType]].
  */
object ActionType {
  /** A "release", or key up action.
    */
  case object Up extends ActionType {
    /** @return false, as the action is a release
      */
    override def toBoolean = false
  }
  /** A "press", or key down action.
    */
  case object Down extends ActionType {
    /** @return true, as the action is a press
      */
    override def toBoolean = true
  }
}
