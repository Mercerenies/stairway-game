
package com.mercerenies.stairway.util

// This object encodes the first few Roman numerals by brute force. I would use the
// general formula (available on Rosetta Code) but I only need the first few numbers.
object Roman {

  def numeral(n: Int): String = n match {
    case  1 => "I"
    case  2 => "II"
    case  3 => "III"
    case  4 => "IV"
    case  5 => "V"
    case  6 => "VI"
    case  7 => "VII"
    case  8 => "VIII"
    case  9 => "IX"
    case 10 => "X"
    case 11 => "XI"
    case 12 => "XII"
    case 13 => "XIII"
    case _ => sys.error("Unknown Roman numeral at " + n)
  }

}
