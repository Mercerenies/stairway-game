
package com.mercerenies.stairway.util

import scala.math.Ordered

abstract class Index extends Ordered[Index] {

  def value: Index.Type

  def +(other: Index.Type): Index = Index.Absolute(this.value + other)
  def -(other: Index.Type): Index = Index.Absolute(this.value - other)

  def -(other: Index): Index.Type = this.value - other.value

  override def compare(that: Index): Int = this.value compare that.value

}

object Index {

  // NOTE: Whatever Index.Type is, it should definitely have an Integral[T] instance, as well as
  //       supporting both negative and positive numbers. If you change it in the future, verify
  //       these conditions.
  type Type = Int

  case class Absolute(private val _value: Index.Type) extends Index {
    override def value = _value
  }

}
