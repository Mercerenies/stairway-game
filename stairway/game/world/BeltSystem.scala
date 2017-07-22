
package com.mercerenies.stairway
package game.world

import game.{StandardGame, GameData}
import stat.ImprovableStats
import game.belt._
import space._
import enemy._
import product.item._
import product.Scroll
import luck.{DiceValue, DiceNumbers}
import scala.util.Random
import scala.collection.immutable.Stream

class BeltSystem(
  master: StandardGame.Master,
  seed: Option[Long] = None) {
  import util.RandomImplicits._
  import BeltSystem._

  private class SpaceGenerator(
    feed: GeneratorFeed,
    val space: Space,
    override val minTimer: Int,
    override val maxTimer: Int)
      extends AbstractGenerator(feed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed] {
    override def nextSpace() = space
  }

  private object RedGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed]
      with LeadInGenerator[GeneratorFeed]
      with EraLocalGenerator[GeneratorFeed] {

    private var doubleCounter = 0
    private var tripleCounter = 0
    private var quadrupleCounter = 0

    def doubleFreq = master.era match {
      case 1 => 50
      case 2 => 40
      case 3 => 40
      case 4 => 20
      case 5 => 20
      case _ => NoGenerate
    }
    def tripleFreq = master.era match {
      case 1 => 100
      case 2 => 100
      case 3 => 100
      case 4 => 100
      case 5 => 100
      case _ => NoGenerate
    }
    def quadrupleFreq = master.era match {
      case 1 => 100
      case 2 => 100
      case 3 => 100
      case 4 => 100
      case 5 => 100
      case _ => NoGenerate
    }

    override def minTimer = master.era match {
      case 1 => 10
      case 2 => 10
      case 3 => 10
      case 4 => 10
      case 5 => 9
      case _ => NoGenerate
    }
    override def maxTimer = master.era match {
      case 1 => 15
      case 2 => 14
      case 3 => 14
      case 4 => 14
      case 5 => 14
      case _ => NoGenerate
    }

    override def leadIn() = master.era match {
      case 1 => 0
      case 2 => 0
      case 3 => random.nextOf(0, 0, 1)
      case 4 => random.nextOf(0, 0, 1)
      case 5 => random.nextOf(0, 0, 1)
      case _ => 0
    }

    override def nextSpace() = {
      doubleCounter += 1
      if (doubleCounter >= doubleFreq) {
        doubleCounter = 0
        tripleCounter += 1
        if (tripleCounter >= tripleFreq) {
          tripleCounter = 0
          quadrupleCounter += 1
          if (quadrupleCounter >= quadrupleFreq) {
            quadrupleCounter = 0
            RedSpace(RedSpace.Quadruple)
          } else {
            RedSpace(RedSpace.Triple)
          }
        } else {
          RedSpace(RedSpace.Double)
        }
      } else {
        RedSpace(RedSpace.Single)
      }
    }

    def mirror = (doubleCounter, tripleCounter, quadrupleCounter)

    def unmirror(arg: (Int, Int, Int)): Unit = {
      doubleCounter    = arg._1
      tripleCounter    = arg._2
      quadrupleCounter = arg._3
    }

  }

  private object EnemySeqGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with EnemySequenceGenerator[GeneratorFeed]
      with EraLocalGenerator[GeneratorFeed] {

    type EnemySpawnArg = (() => Enemy, Seq[Int])

    private def basicRat() = new Rat(master, Enemy.entropy(random))
    private def basicPear() = new Pear(master, Enemy.entropy(random))
    private def basicSpider() = new Spider(master, Enemy.entropy(random))
    private def basicHelmetImp() = new HelmetImp(master, Enemy.entropy(random))
    private def basicMilk() = new UnspilledMilk(master, Enemy.entropy(random))
    private def basicCone() = new GrumpyCone(master, Enemy.entropy(random))
    private def basicBird() = new Birdbrain(master, Enemy.entropy(random))

    private def ratTeam() = new EnemyTeam(master, basicRat(), basicRat())
    private def spiderTeam() = new EnemyTeam(master, basicSpider(), basicSpider())
    private def spiderTeam3() = new EnemyTeam(master, basicSpider(), basicSpider(), basicSpider())
    private def ratTeam3() = new EnemyTeam(master, basicRat(), basicRat(), basicRat())
    private def pearRatTeam() = new EnemyTeam(master, basicPear(), basicRat(), basicRat())
    private def pearRatTeam3() = new EnemyTeam(master, basicPear(), basicRat(), basicRat(), basicRat())
    private def pearTeam() = new EnemyTeam(master, basicPear(), basicPear())
    private def pearTeam3() = new EnemyTeam(master, basicPear(), basicPear(), basicPear())
    private def impSpiderTeam() = new EnemyTeam(master, basicHelmetImp(), basicSpider())
    private def impPearTeam() = new EnemyTeam(master, basicHelmetImp(), basicPear())
    private def impMilkTeam() = new EnemyTeam(master, basicHelmetImp(), basicMilk())
    private def coneTeam() = new EnemyTeam(master, basicCone(), basicCone())

    private val eraSettings: Array[Seq[EnemySpawnArg]] = Array(
      List(
        entry(basicRat(), 3, 3, 4)
      ),
      List(
        entry(ratTeam(), 3, 4, 5),
        entry(ratTeam(), 3, 4, 5),
        entry(basicPear(), 4, 5, 5),
        entry(basicPear(), 4, 5, 5),
        entry(basicRat(), 3, 3, 4)
      ),
      List(
        entry(basicSpider(), 4, 5, 5),
        entry(spiderTeam(), 5, 6, 6),
        entry(ratTeam3(), 4, 5, 5),
        entry(pearRatTeam(), 5, 5, 6),
        entry(pearTeam(), 4, 4, 5),
        entry(basicHelmetImp(), 5, 6, 7)
      ),
      List(
        entry(spiderTeam(), 4, 4, 5),
        entry(pearTeam3(), 4, 4, 5),
        entry(impPearTeam(), 5, 6, 7),
        entry(basicMilk(), 3, 3, 4),
        entry(basicCone(), 6, 6, 7)
      ),
      List(
        entry(basicBird(), 4, 5, 5),
        ////
        entry(impSpiderTeam(), 5, 6, 6),
        entry(coneTeam(), 5, 5, 6),
        entry(spiderTeam3(), 3, 4, 4)
      )
    )

    private def entry(func: => Enemy, poss: Int*): EnemySpawnArg =
      (() => func, poss)

    private def settings(era: Int) = eraSettings(era - 1)

    override def minTimer = master.era match {
      case 1 => 20
      case 2 => 16
      case 3 => 15
      case 4 => 15
      case 5 => 15
      case _ => NoGenerate
    }

    override def maxTimer = master.era match {
      case 1 => 25
      case 2 => 21
      case 3 => 21
      case 4 => 21
      case 5 => 21
      case _ => NoGenerate
    }

    override def ready: Boolean = eraSettings.isDefinedAt(master.era - 1) && super.ready

    override def nextEnemy() = random.nextOf(settings(master.era): _*) match {
      case (enemy, paces) => (enemy(), random.nextOf(paces: _*))
    }

  }

  private object FruitGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed]
      with EraLocalGenerator[GeneratorFeed] {

    override def minTimer = master.era match {
      case 1 => 9
      case 2 => 10
      case 3 => 15
      case 4 => 16
      case 5 => 16
      case _ => NoGenerate
    }
    override def maxTimer = master.era match {
      case 1 => 18
      case 2 => 20
      case 3 => 23
      case 4 => 23
      case 5 => 23
      case _ => NoGenerate
    }

    override def nextSpace(): Space = FruitSpace

  }

  private object RecoveryGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed]
      with EraLocalGenerator[GeneratorFeed] {

    val cycle = util.cycle(IncomeSpace, HealthSpace).iterator

    override def minTimer = master.era match {
      case 1 => 7
      case 2 => 7
      case 3 => 7
      case 4 => 8
      case 5 => 10
      case _ => NoGenerate
    }
    override def maxTimer = master.era match {
      case 1 => 10
      case 2 => 10
      case 3 => 10
      case 4 => 11
      case 5 => 15
      case _ => NoGenerate
    }

    override def nextSpace(): Space = cycle.next()

  }

  private object TaxGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed]
      with LeadInGenerator[GeneratorFeed]
      with EraLocalGenerator[GeneratorFeed] {

    override def minTimer = master.era match {
      case 1 => 10
      case 2 => 10
      case 3 => 10
      case 4 => 15
      case 5 => 15
      case _ => NoGenerate
    }
    override def maxTimer = master.era match {
      case 1 => 25
      case 2 => 25
      case 3 => 25
      case 4 => 23
      case 5 => 23
      case _ => NoGenerate
    }

    override def leadIn() = master.era match {
      case 1 => 0
      case 2 => 0
      case 3 => 0
      case 4 => random.nextOf(0, 0, 0, 1)
      case 5 => random.nextOf(0, 0, 0, 1)
      case _ => 0
    }

    override def nextSpace(): Space = TaxSpace

  }

  private object ItemGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed]
      with EraLocalGenerator[GeneratorFeed] {

    override def minTimer = master.era match {
      case 1 => NoGenerate
      case 2 => 20
      case 3 => 18
      case 4 => 16
      case 5 => 14
      case _ => NoGenerate
    }
    override def maxTimer = master.era match {
      case 1 => NoGenerate
      case 2 => 25
      case 3 => 25
      case 4 => 23
      case 5 => 19
      case _ => NoGenerate
    }

    def itemSampler: Seq[Item] = master.era match {
      case 1 => List()
      case 2 => List(Coffee, Sundae, ThrowingKnife)
      case 3 => List(Spikes, Sundae, Coffee, ThrowingKnife)
      case 4 => List(Spikes, Coffee, ThrowingKnife, ChiliPepper)
      case 5 => List(Anchor, SpearheadArrow, SilverFeather, ChiliPepper)
      case _ => List()
    }

    override def ready = !itemSampler.isEmpty && super.ready

    def nextItem(): Item = random.nextOf(itemSampler: _*)

    override def nextSpace(): Space = ItemSpace(nextItem(), nextItem(), nextItem())

  }

  private object MysteryGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed]
      with EraLocalGenerator[GeneratorFeed] {

    override def minTimer = master.era match {
      case 1 => 13
      case 2 => 13
      case 3 => 13
      case 4 => 13
      case 5 => 12
      case _ => NoGenerate
    }
    override def maxTimer = master.era match {
      case 1 => 18
      case 2 => 22
      case 3 => 26
      case 4 => 26
      case 5 => 25
      case _ => NoGenerate
    }

    def boxSampler: Seq[Int] = master.era match {
      case 1 => List(3)
      case 2 => List(3, 4, 4)
      case 3 => List(3, 4, 4, 5, 5, 6, 6)
      case 4 => List(4, 5, 5, 6)
      case 5 => List(4, 5, 6, 6)
      case _ => List(1)
    }

    def nextBoxes(): Int = random.nextOf(boxSampler: _*)

    override def nextSpace(): Space = MysterySpace(nextBoxes())

  }

  private object LottoGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed]
      with EraLocalGenerator[GeneratorFeed] {
    import Ordering.Implicits._

    override def minTimer = master.era match {
      case 1 => 25
      case 2 => 20
      case 3 => 19
      case 4 => 17
      case 5 => 17
      case _ => NoGenerate
    }
    override def maxTimer = master.era match {
      case 1 => 28
      case 2 => 27
      case 3 => 28
      case 4 => 28
      case 5 => 33
      case _ => NoGenerate
    }

    def lottoSampler: Seq[(Int, DiceValue)] = master.era match {
      case 1 => List((3, DiceValue(9)), (3, DiceValue(10)), (3, DiceValue(11)), (3, DiceValue(11)))
      case 2 => List((3, DiceValue(10)), (3, DiceValue(10)), (3, DiceValue(11)), (3, DiceValue(12)))
      case 3 => List((3, DiceValue(11)), (3, DiceValue(12)), (3, DiceValue(13)))
      case 4 => List((3, DiceValue(12)), (3, DiceValue(13)), (3, DiceValue(13)))
      case 5 => List((4, DiceValue(15)), (4, DiceValue(16)))
      case _ => List((1, DiceValue(999)))
    }

    def houseRules(x: (Int, DiceValue)): Int = x match {
      case (n, v) => math.max((1 / DiceNumbers(n).satisfy(_.sum >= v)).floor.toInt, 2)
    }

    def nextLotto(): LotterySpace = random.nextOf(lottoSampler: _*) match {
      case (n, v) => LotterySpace(n, v, houseRules((n, v)))
    }

    override def nextSpace(): Space = nextLotto()

  }

  private object IceGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed]
      with EraLocalGenerator[GeneratorFeed] {

    override def minTimer = master.era match {
      case 1 => 28
      case 2 => 26
      case 3 => 24
      case 4 => 22
      case 5 => 22
      case _ => NoGenerate
    }
    override def maxTimer = master.era match {
      case 1 => 44
      case 2 => 44
      case 3 => 44
      case 4 => 40
      case 5 => 36
      case _ => NoGenerate
    }

    override def nextSpace(): Space = IceSpace

  }

  private object DojoGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed]
      with EraLocalGenerator[GeneratorFeed] {

    val allUpgrades: Seq[ImprovableStats.UpgradeSlot[_]] = random.shuffle(master.stats.levels.standardUpgrades)
    val upgradeIter: Iterator[ImprovableStats.UpgradeSlot[_]] = util.cycle(allUpgrades: _*).iterator
    private var index: Int = 0
    private val indexMax: Int = allUpgrades.size

    private def upgradeNext() = {
      index = (index + 1) % indexMax
      upgradeIter.next()
    }

    override def minTimer = master.era match {
      case 1 => NoGenerate
      case 2 => 20
      case 3 => 21
      case 4 => 21
      case 5 => 22
      case _ => NoGenerate
    }
    override def maxTimer = master.era match {
      case 1 => NoGenerate
      case 2 => 25
      case 3 => 26
      case 4 => 26
      case 5 => 27
      case _ => NoGenerate
    }

    def currentIndex = index

    def advanceTo(n: Int) = {
      if (n < 0 || n >= indexMax)
        sys.error("Invalid index in advanceTo")
      while (index != n)
        upgradeNext()
    }

    private def cyclicLevels(): Seq[ImprovableStats.UpgradeSlot[_]] =
      List(upgradeNext(), upgradeNext())

    private def randomLevels(non: Seq[ImprovableStats.UpgradeSlot[_]]): Seq[ImprovableStats.UpgradeSlot[_]] = {
      val upgrades = allUpgrades.filterNot(non.contains(_))
      val fst = random.nextOf(upgrades: _*)
      val snd = random.nextOf(upgrades.filterNot(_ == fst): _*)
      List(fst, snd)
    }

    private def levelsSet(): Seq[ImprovableStats.UpgradeSlot[_]] = {
      val cyclic = cyclicLevels()
      val rand = randomLevels(cyclic)
      cyclic ++ rand
    }

    override def nextSpace() = DojoSpace(random.shuffle(levelsSet()): _*)

  }

  private object FruitTheftGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed]
      with ClusterGenerator[GeneratorFeed]
      with LeadInGenerator[GeneratorFeed]
      with EraLocalGenerator[GeneratorFeed] {

    override def minTimer = master.era match {
      case 1 => NoGenerate
      case 2 => 12
      case 3 => 12
      case 4 => 12
      case 5 => 12
      case _ => NoGenerate
    }
    override def maxTimer = master.era match {
      case 1 => NoGenerate
      case 2 => 24
      case 3 => 25
      case 4 => 24
      case 5 => 25
      case _ => NoGenerate
    }

    override def leadIn() = master.era match {
      case 1 => 0
      case 2 => 0
      case 3 => random.nextOf(0, 0, 0, 1)
      case 4 => random.nextOf(0, 0, 0, 1)
      case 5 => random.nextOf(0, 0, 0, 1)
      case _ => 0
    }

    override def clusterSize() = master.era match {
      case 1 => 1
      case 2 => 1
      case 3 => 1
      case 4 => random.nextOf(1, 1, 1, 2)
      case 5 => random.nextOf(1, 2)
      case _ => 1
    }

    override def nextSpace(): Space = FruitTheftSpace

  }

  private object BlockedGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed]
      with LeadInGenerator[GeneratorFeed]
      with EraLocalGenerator[GeneratorFeed] {

    override def minTimer = master.era match {
      case 1 => NoGenerate
      case 2 => NoGenerate
      case 3 => NoGenerate
      case 4 => NoGenerate
      case 5 => 19
      case _ => NoGenerate
    }
    override def maxTimer = master.era match {
      case 1 => NoGenerate
      case 2 => NoGenerate
      case 3 => NoGenerate
      case 4 => NoGenerate
      case 5 => 30
      case _ => NoGenerate
    }

    override def leadIn() = master.era match {
      case 1 => 0
      case 2 => 0
      case 3 => 0
      case 4 => 0
      case 5 => random.nextOf(0, 0, 1)
      case _ => 0
    }

    override def nextSpace(): Space = BlockedSpace()

  }

  private object ScrollGeneratorA
      extends AbstractGenerator(BaseFeed, random)
      with FixedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed]
      with CounterGenerator[GeneratorFeed]
      with EraLocalGenerator[GeneratorFeed] {

    val scrolls = Array(
       new Scroll(
         Scroll.LevelEffect("HP +10", _.health.buffBy(10)),
         Scroll.LevelEffect("HP +20", _.health.buffBy(20)),
         Scroll.LevelEffect("HP +50", _.health.buffBy(50))
       ),
      new Scroll(
        Scroll.LevelEffect("Energy +10", _.energy.buffBy(10)),
        Scroll.LevelEffect("Energy +20", _.energy.buffBy(20)),
        Scroll.LevelEffect("Energy +50", _.energy.buffBy(50))
      ),
      new Scroll(
        Scroll.LevelEffect("Luck +10%", _.luck.buffBy(0.10)),
        Scroll.LevelEffect("Luck +20%", _.luck.buffBy(0.20)),
        Scroll.LevelEffect("Luck +50%", _.luck.buffBy(0.50))
      )
    )

    override def fixedTimer = master.era match {
      case 1 => NoGenerate
      case 2 => if (counter <= 0) 20 else NoGenerate
      case 3 => if (counter <= 1) 50 else NoGenerate
      case 4 => if (counter <= 2) 80 else NoGenerate
      case 5 => NoGenerate
      case _ => NoGenerate
    }

    override def nextSpace() =
      ScrollSpace(counter, scrolls: _*)

    def mirror: GameData.ScrollState =
      GameData.ScrollState(
        counter,
        (scrolls(0).used, scrolls(1).used, scrolls(2).used)
      )

    def unmirror(arg: GameData.ScrollState) = {
      counter = arg.age
      scrolls(0).used = arg.used._1
      scrolls(1).used = arg.used._2
      scrolls(2).used = arg.used._3
    }

  }

  private object SpecialGenerator
      extends AbstractGenerator(BaseFeed, random)
      with TimedGenerator[GeneratorFeed]
      with SimpleSpaceGenerator[GeneratorFeed]
      with EraLocalGenerator[GeneratorFeed] {

    override def minTimer = master.era match {
      case 1 => NoGenerate
      case 2 => NoGenerate
      case 3 => 12
      case 4 => 10
      case 5 => 10
      case _ => NoGenerate
    }
    override def maxTimer = master.era match {
      case 1 => NoGenerate
      case 2 => NoGenerate
      case 3 => 25
      case 4 => 23
      case 5 => 21
      case _ => NoGenerate
    }

    def eraSpace(era: Int): Space = era match {
      case 1 => NeutralSpace
      case 2 => NeutralSpace
      case 3 => WebSpace
      case 4 => MilkGlassSpace(3)
      case 5 => LeapSpace()
      case _ => NeutralSpace
    }

    def startEra: Int = 3

    override def nextSpace(): Space =
      if (random.nextInt(5) == 0)
        eraSpace(random.nextOf((startEra to master.era): _*))
      else
        eraSpace(master.era)
  }

  private object BaseFeed extends GeneratorFeed(master) {

    override lazy val generators: Seq[Generator[GeneratorFeed]] =
      List(
        ScrollGeneratorA, // Scrolls
        RecoveryGenerator, // Recovery
        MysteryGenerator, LottoGenerator, // Gambling
        FruitGenerator, ItemGenerator, // Shopping
        DojoGenerator, // Training
        TaxGenerator, RedGenerator, FruitTheftGenerator, BlockedGenerator, // Negative (I)
        IceGenerator, SpecialGenerator, // Negative (II)
        EnemySeqGenerator // Fighting
      )

    override def default(index: util.Index): Space =
      if (index < util.Index.Absolute(0)) EmptySpace else NeutralSpace

  }

  private object BossFeedSystem extends BossSystem[BaseFeed.type](master, BaseFeed) {

    override lazy val bosses: Seq[BossEnemy] = List(
      new Ratticus(master),
      new ShakesPear(master),
      new Arachula(master),
      new GenghisKone(master),
      new JoanOfLark(master)
    )

    lazy val stoppingPoints: Seq[Int] = List(50, 80, 110, 140, 170)

    private var lastSwitch: util.Index = util.Index.Absolute(0)

    override def timeToSwitch(index: util.Index): Boolean =
      stoppingPoints andThen (index >= lastSwitch + _ - 1) applyOrElse (master.era - 1, { (_: Int) => false })

    def tillNextBoss: util.Index.Type = {
      val index = master.player.occupiedPosition
      stoppingPoints andThen
        (lastSwitch + _ - index) andThen
        (math.max(_, 0)) applyOrElse
        (master.era - 1, { (_: Int) => 0 })
    }

    override def onSwitch(arg: AlternatingFeed.Alternate): Unit = {
      super.onSwitch(arg)
      // If we're switching back to the regular feed, set the basis point
      if (arg == regularFeed)
        lastSwitch = util.Index.Absolute(AltFeed.topDefined)
    }

    def lastBoss = lastSwitch.value

    def lastBoss_=(b: Int) = {
      lastSwitch = util.Index.Absolute(b)
    }

  }

  def this(master: StandardGame.Master, seed: Long) = this(master, Some(seed))

  lazy val random: Random = seed match {
    case None => new Random()
    case Some(x) => new Random(x)
  }

  val belt: OverrideConveyer[ConveyerFeed] =
    new OverrideConveyer(master, BossFeedSystem.AltFeed)

  def eraChanged(newEra: Int): Unit = {
    BaseFeed.eraChanged(newEra)
  }

  def resetState(): Unit = {
    BaseFeed.resetState()
  }

  def freezeBase(): Unit = {
    BaseFeed.freeze()
  }

  def tillNextBoss: util.Index.Type = BossFeedSystem.tillNextBoss

  def mirror: GameData.Belt = {
    val (d, t, q) = RedGenerator.mirror
    GameData.Belt(
      d, t, q,
      DojoGenerator.currentIndex,
      List(ScrollGeneratorA.mirror),
      BossFeedSystem.lastBoss
    )
  }

  def unmirror(belt: GameData.Belt) = {
    RedGenerator.unmirror((belt.double, belt.triple, belt.quadruple))
    DojoGenerator.advanceTo(belt.dojoIndex)
    ScrollGeneratorA.unmirror(belt.scrolls.applyOrElse(0, (_: Int) => GameData.ScrollState.Default))
    BossFeedSystem.lastBoss = belt.lastSwitch
  }

}

object BeltSystem {

  val NoGenerate = Int.MaxValue

}
