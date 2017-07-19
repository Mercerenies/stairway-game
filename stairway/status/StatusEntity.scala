
package com.mercerenies.stairway.status

trait StatusEntity {

  private var _statuses: List[StatusEffect] = List()

  def statuses: List[StatusEffect] = _statuses

  def checkStatuses(): Unit = {
    _statuses = _statuses.filterNot(_.expired)
  }

  def afflictStatus(status: StatusEffect): Unit = {
    _statuses = status :: _statuses
  }

  def hasStatus(func: StatusEffect => Boolean): Boolean =
    !statuses.find(func).isEmpty

  def isFlying: Boolean =
    statuses.exists { _.isFlying }

}
