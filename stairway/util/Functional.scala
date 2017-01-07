
package com.mercerenies.stairway.util

object Functional {

  def recurse[U](f: (() => U) => U): Unit = {
    def helper(): U = { f(helper) }
    helper()
  }

}
