
package com.mercerenies.stairway.luck

case class DiceNumbers(count: Int) extends AnyVal {

  def enumerate: List[List[DiceValue]] =
    if (count <= 0)
      List(List())
    else
      for (
        head <- (1 to 6).toList;
        tail <- DiceNumbers(count - 1).enumerate
      ) yield DiceValue(head) :: tail

  def satisfy(f: (List[DiceValue]) => Boolean): Double = {
    val enum = enumerate
    enum.count(f).toDouble / enum.size.toDouble
  }

  def minimum: DiceValue = enumerate.map(_.sum).min
  def maximum: DiceValue = enumerate.map(_.sum).max

}

