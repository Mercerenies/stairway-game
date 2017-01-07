
package com.mercerenies.stairway.luck

case class DiceValue(value: Int) extends AnyVal {

  def to(other: DiceValue): List[DiceValue] = (this.value to other.value).map(DiceValue(_)).toList
  def until(other: DiceValue): List[DiceValue] = (this.value until other.value).map(DiceValue(_)).toList

}

object DiceValue {

  implicit object DiceValueIsIntegral extends Numeric[DiceValue] {
    override def compare(x: DiceValue, y: DiceValue): Int =
      x.value - y.value
    override def fromInt(x: Int) = DiceValue(x)
    override def plus(x: DiceValue, y: DiceValue): DiceValue = DiceValue(x.value + y.value)
    override def minus(x: DiceValue, y: DiceValue): DiceValue = plus(x, negate(y))
    override def negate(x: DiceValue): DiceValue = DiceValue(- x.value)
    override def times(x: DiceValue, y: DiceValue): DiceValue = DiceValue(x.value * y.value)
    override def toDouble(x: DiceValue): Double = x.value
    override def toFloat(x: DiceValue): Float = x.value
    override def toInt(x: DiceValue): Int = x.value
    override def toLong(x: DiceValue): Long = x.value
  }

}
