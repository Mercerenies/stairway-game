
package com.mercerenies.stairway.status

/** A status entity can be afflicted with status effects.
  *
  * This trait describes all of the potential effects of statuses and
  * provides the means for objects which can be afflicted by them to
  * check their current status.
  */
trait StatusEntity {

  private var _statuses: List[StatusEffect] = List()

  /** @return a list of active status effects
    */
  def statuses: List[StatusEffect] = _statuses

  /** Checks the current list of status effects and removes any expired
    * statuses. This method should be called ''manually'' after
    * performing the status' effect.
    */
  def checkStatuses(): Unit = {
    _statuses = _statuses.filterNot(_.expired)
  }

  /** Afflicts the entity with a new status effect.
    *
    * @param status the status effect
    */
  def afflictStatus(status: StatusEffect): Unit = {
    _statuses = status :: _statuses
  }

  /** Cures the entity of every status effect satisfying the predicate.
    *
    * @param func a Boolean predicate
    */
  def cureStatus(func: StatusEffect => Boolean): Unit = {
    _statuses = _statuses.filterNot(func)
  }

  /** Returns whether the entity has any status effects satisfying the
    * predicate.
    *
    * @param func a Boolean predicate
    */
  def hasStatus(func: StatusEffect => Boolean): Boolean =
    !statuses.find(func).isEmpty

  /** Returns whether the entity has any status effects that enable it
    * to fly.
    *
    * @return whether the entity is flying as a result of statuses
    */
  def isFlying: Boolean =
    statuses.exists { _.isFlying }

  /** Returns whether the entity is able to use items. An entity can use
    * items unless ''any'' of its statuses say otherwise.
    *
    * @return whether the entity can use items
    */
  def canUseItems: Boolean =
    statuses.forall { _.canUseItems }

}
