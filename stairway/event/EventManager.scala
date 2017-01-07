
package com.mercerenies.stairway.event

import scala.collection.mutable.{Set, HashSet}

class EventManager[Arg] {

  type EventType = Event[Arg]

  private val _events: Set[EventType] = new HashSet

  def +=(obj: EventType) = _events += obj
  def -=(obj: EventType) = _events remove obj

  def invoke(arg: Arg) = {
    _events.foreach(_.call(arg))
  }

}
