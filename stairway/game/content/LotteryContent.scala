
package com.mercerenies.stairway
package game.content

import luck._
import ui.{Drawable, Triangle}
import util.{PointImplicits, Rectangle, Numeral}
import space.LotterySpace
import action.{MouseClick, MouseButton, ActionType}
import java.awt.{List => _, _}

class LotteryContent(contentArea: ContentArea, val space: LotterySpace)
    extends AbstractContent(contentArea) with DiceContainer with ContentHeader {
  import util.PointImplicits._
  import util.GraphicsImplicits._
  import Ordering.Implicits._

  abstract class State extends Drawable {
    def step(): Unit
    def click(pos: (Int, Int)): Unit
    def showHeader: Boolean = true
  }

  private object SliderState extends State {

    val (upArrow, centerField, downArrow, goButton) = {
      val crect = contentArea.rect
      val up     = Rectangle(- 32, - 96,   32, - 64)
      val center = Rectangle(- 32, - 32,   32,   32)
      val down   = Rectangle(- 32,   64,   32,   96)
      val go     = Rectangle(  96,   96,  160,  128)
      (
        up.shift(crect.centerX, crect.centerY),
        center.shift(crect.centerX, crect.centerY),
        down.shift(crect.centerX, crect.centerY),
        go.shift(crect.centerX, crect.centerY)
      )
    }
    val boldFont = new Font(Font.SANS_SERIF, Font.BOLD, 24)
    val font = new Font(Font.SANS_SERIF, Font.PLAIN, 24)
    private var _number: Int = 0

    lazy val upArrowTriangle = new Triangle(
      upArrow.xmin, upArrow.ymax,
      upArrow.xmax, upArrow.ymax,
      upArrow.centerX, upArrow.ymin
    )
    lazy val downArrowTriangle = new Triangle(
      downArrow.xmin, downArrow.ymin,
      downArrow.xmax, downArrow.ymin,
      downArrow.centerX, downArrow.ymax
    )

    def index: Int = _number
    def index_=(value: Int): Unit = {
      val moneyMax = (master.stats.money / 5.0).ceil.toInt
      val absMax = 20
      _number = util.clamp(value, 0, math.min(moneyMax, absMax))
    }

    def number: Int = math.min(index * 5, master.stats.money)

    override def click(pos: (Int, Int)): Unit = {
      if (pos within upArrow) {
        index += 1
      } else if (pos within downArrow) {
        index -= 1
      } else if (pos within goButton) {
        if (number > 0) {
          DiceState.init(number)
          master.stats.money -= number
          _state = DiceState
        }
      }
    }

    override def draw(graph: Graphics2D, rect: Rectangle): Unit = {

      graph.setColor(Color.white)
      graph.fill(upArrowTriangle)
      graph.fill(downArrowTriangle)
      graph.fill(centerField)
      graph.fill(goButton)

      graph.setColor(Color.black)
      graph.draw(upArrowTriangle)
      graph.draw(downArrowTriangle)
      graph.draw(centerField)
      graph.draw(goButton)

      graph.setFont(boldFont)
      val metrics = graph.getFontMetrics()

      val string = f"$$$number%02d"
      val textWidth = metrics.stringWidth(string)
      val textHeight = metrics.getAscent()
      graph.drawString(
        string,
        centerField.centerX - textWidth / 2,
        centerField.centerY + textHeight / 2
      )

      graph.setFont(font)
      val metrics1 = graph.getFontMetrics()

      if (number > 0)
        graph.setColor(Color.black)
      else
        graph.setColor(Color.gray)
      val string1 = "Roll"
      val textWidth1 = metrics.stringWidth(string1)
      val textHeight1 = metrics.getAscent()
      graph.drawString(
        string1,
        goButton.centerX - textWidth1 / 2,
        goButton.centerY + textHeight1 / 2
      )

    }

    override def step(): Unit = {}

  }

  private object DiceState extends State {
    import util.RandomImplicits._

    private var timer = 60
    private var wager = 0

    def init(n: Int): Unit = {
      master.karmaBar.freeze()
      val nums = DiceNumbers(space.diceCount)
      val outcome = master.luck.evaluateLuck(LotteryContent.LuckWeight, odds)
      val total = if (outcome) (space.toBeat) to (nums.maximum) else (nums.minimum) until (space.toBeat)
      val numbers = DiceWaterfall.getValues(space.diceCount, util.rand.nextOf(total: _*))
      wager = n
      for (n <- numbers) spawnDie(n)
    }

    override def draw(graph: Graphics2D, rect: Rectangle): Unit = {}
    override def click(pos: (Int, Int)): Unit = {}
    override def step(): Unit = diceValue match {
      case None => {}
      case Some(n) => {
        if (timer > 0) {
          timer -= 1
        } else {
          clearDice()
          if (n >= space.toBeat)
            master.stats.money += wager * space.multiplier
          master.karmaBar.thaw()
          _state = FinishedState
        }
      }
    }

  }

  private object FinishedState extends State {
    override def draw(graph: Graphics2D, rect: Rectangle): Unit = {}
    override def click(pos: (Int, Int)): Unit = {}
    override def step(): Unit = {}
    override def showHeader: Boolean = false
  }

  private var _state: State = SliderState

  private lazy val augmentedCrit = master.luck.augmentedOdds(odds)

  private def phrase0: String = s"Place your wager and roll ${space.diceCount} dice."
  private def phrase1: String = s"If the sum is at least ${space.toBeat.value},"
  private def phrase2: String = s"you win ${Numeral.times(space.multiplier)} your wager! [Odds: ${Probability(augmentedCrit)}]"
  override def headerText: List[String] =
    if (_state.showHeader)
      List(phrase0, phrase1, phrase2)
    else
      Nil

  override def step(): Unit = {
    stepDice()
    _state.step()
  }

  override def click(click: MouseClick): Unit = click match {
    case MouseClick(MouseButton.Left, ActionType.Up, pos) => _state.click(pos)
    case _ => {}
  }

  override def draw(graph: Graphics2D, rect: Rectangle): Unit = {
    _state.draw(graph, rect)
    drawDice(graph)
    drawHeader(graph, rect)
  }

  override def isIdle: Boolean = !hasDice

  def odds: Double = DiceNumbers(space.diceCount).satisfy(_.sum >= space.toBeat)

}

object LotteryContent {
  val LuckWeight = 0.1
}
