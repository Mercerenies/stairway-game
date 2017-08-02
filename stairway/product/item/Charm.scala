
package com.mercerenies.stairway
package product.item

import space.RedSpace

trait Charm {

  def isProtectedAgainst(space: RedSpace.Severity): Boolean

}
