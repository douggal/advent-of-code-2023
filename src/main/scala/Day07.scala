import scala.io.Source
import java.time.{Duration, Instant}
import scala.collection.mutable.{ArrayBuffer, Map}

/** Advent of Code 2023 Day 7
 *
 * Defines a class, its companion object and a runner method for
 * the AoC Day 7 puzzles.
 *
 * Created 7 Dec 2023
 * Link: https://adventofcode.com/2023/day/7
 */

class Day07 private (val title: String, val runType: Int ):

    //var title: String = "Advent of Code 2023"
    //var runType: Int = 1 // Default to test data
    def run():Unit = Day07.solution(runType)

    override def toString: String = s"Class ${Day07.puzzleTitle}"

end Day07

object Day07:

    val day = "07"
    val puzzleTitle = "Day 7: Camel Cards"

    // input data files
    private val testData: String = s"$day-test.txt"
    private val realData: String = s"$day-input.txt"

    // Factory methods
    // a one-arg constructor
    def apply(runType: Int): Day07 = {
        new Day07("AoC", runType)
    }

    // a two-arg constructor
    def apply(title: String, runType: Int): Day07 = {
        new Day07(title, runType)
    }

    private def solution(runType: Int): Unit = {

        println(s"--- Advent of Code 2023 ---")
        println(s"--- $puzzleTitle ---\n")

        // Read the puzzle input data file
        val filename = if (runType == 1) testData else realData
        print("Attempting to read input data file using ")
        if runType == 1 then
            println("TEST DATA ... ")
        else
            println("REAL INPUT DATA ...")

        // simple text file read:  Jan-Pieter van den Heuvel [Saving Christmas Using Scala](https://www.youtube.com/watch?v=tHU36gQ5iAI)
        val input = Source.fromResource(filename).getLines().toVector

        println("\nData Quality Control:")
        println(s"  Input file name: $filename")
        println(s"  Each line is a: ${input.head.getClass}")
        println(s"  Number lines: ${input.length}")
        println(s"  Number items per line: ${input.head.count(_ => true)}")
        println(s"  First line: ${input.head}")
        if input.size > 1 then
            println(s"  Last line: ${input.last}")
        println

        // ----------------------
        //  Common to both parts
        // ----------------------
        enum HandType:
            case HighCard, OnePair, TwoPair, Three, FullHouse, Four, Five

        val suits = "AKQJT98765432"
        val valueOfSuits = suits.reverse.zipWithIndex.toMap
        // valueOfSuits.foreach(println)
        val relStrength = suits.map(_.toString).reverse.toList

        val parseHandRE = raw"([\d\w]+) ([\d ]+)".r
        val FiveRE = s"([${suits}])\\1{4}".r.unanchored
        val FourRE = s"([${suits}])\\1{3}".r.unanchored
        val ThreeRE = s"([${suits}])\\1{2}".r.unanchored
        val PairRE = s"([${suits}])\\1{1}".r.unanchored

        case class Hand(cards: String, sorted: String, bid: Int)
        case class TypedHand(cards: String, sorted: String, bid: Int, typ: HandType)

        val listHands = input
            .map(y => y.trim.split(" +"))
            .map(x => Hand(x(0), x(0).sorted, x(1).toInt))
            .toList

        // listHands.foreach(println)

        def typeOfHand(h: Hand): HandType = {
                h.sorted match
                    case FiveRE(c) => HandType.Five
                    case FourRE(c) => HandType.Four
                    case ThreeRE(c) if PairRE.matches(c) => HandType.FullHouse
                    case ThreeRE(c) => HandType.Three
                    case c => /* one pair or two pair */
                        {
                            // Ref: https://stackoverflow.com/questions/61631731/char-count-in-string
                            val counts =
                                c.foldLeft(Map.empty[Char, Int].withDefaultValue(0)) {
                                    (counts, c) => counts.clone.addOne(c, counts(c) + 1)
                                }
                            // counts.foreach(x => println(s"$c: $x"))
                            val pairs = counts.values.count(_ == 2)
                            if pairs == 2 then
                                HandType.TwoPair
                            else if pairs == 1 then
                                HandType.OnePair
                            else
                                HandType.HighCard
                        }
        }

        // listHands.foreach(x => println(s"Hand: ${x.cards}, Type: ${typeOfHand(x)}"))

        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()

        /*
            the first step is to put the hands in order of strength:
          Rank: rule 1) by type of hand,
                rule 2) relative strength of cards

            Note: https://stackoverflow.com/questions/40104898/scala-enumeration-compare-order
         */
        // sort by enum, lowest rank to have lowest index
        // val sortedByType = listHands.sortWith(typeOfHand(_).ordinal < typeOfHand(_).ordinal)
        // sortedByType.foreach(x => println(s"${typeOfHand(x)}, $x"))

        val sortedByType = scala.collection.mutable.Map[HandType, ArrayBuffer[Hand]]()
        for hand <- listHands do
            val typ = typeOfHand(hand)
            if sortedByType.contains(typ) then
                sortedByType(typ) += hand
            else
                sortedByType += (typ -> ArrayBuffer[Hand](hand))

        val rankedHands = ArrayBuffer[Hand]()
        for key <- HandType.values do
            if sortedByType.contains(key) then
                if sortedByType(key).length == 1 then
                    rankedHands += sortedByType(key)(0)
                else
                    /* compare cards */
                    val byStrength = scala.collection.mutable.Map[Long, Hand]()
                    val g = sortedByType(key)
                    for h <- g do
                        var score = 0L
                        for i <- 0 to 4 do
                            score += h.cards(i) + ((valueOfSuits(h.cards(i)) + 1) * Math.pow(10, 4-i).toLong)
                        byStrength += (score -> h)

                    val ks = byStrength.keys.toList.sorted
                    for k <- ks do
                        rankedHands += byStrength(k)

        /* should have list with index from 0 to N of each hand in order by hand type and card strength */
        /*
             Each hand wins an amount equal to its bid multiplied by its rank,
             where the weakest hand gets rank 1
          */
        var winnings = 0
        for h <- rankedHands.zipWithIndex do
            // println(s"${h._1.bid} * ${h._2 + 1}")
            winnings += h._1.bid * (h._2 + 1)

        val answerP1 = winnings
        println(s"Part 1: Find the rank of every hand in your set. What are the total winnings? A: $answerP1")
        val delta1 = Duration.between(p1T0, Instant.now())
        println(s"Part 1 run time approx ${delta1.toMillis} milliseconds\n")


        // ----------
        //  Part Two
        // ----------
        val p2T0 = Instant.now()

        println(s"Part 2: TBD ???")

        val delta2 = Duration.between(p2T0, Instant.now())
        println(f"Part 2 run time approx ${delta2.toMillis} milliseconds")

        // errata...for visualization with Excel chart

    }

end Day07
