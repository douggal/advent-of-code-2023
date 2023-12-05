import scala.io.Source
import java.time.{Duration, Instant}

/** Advent of Code 2023 Day 4
 *
 * Defines a class, its companion object and a runner method for
 * the AoC Day 4 puzzles.
 *
 * Created 4 Dec 2023
 * Link: https://adventofcode.com/2023/day/4
 */

class Day04 private (val title: String, val runType: Int ):

    //var title: String = "Advent of Code 2023"
    //var runType: Int = 1 // Default to test data
    def run():Unit = Day04.runPuzzle(runType)

    override def toString: String = s"Class ${Day04.puzzleTitle}"

end Day04

object Day04:

    val day = "04"
    val puzzleTitle = "--- Day 4: Scratchcards ---"

    // input data files
    private val testData: String = s"$day-test.txt"
    private val realData: String = s"$day-input.txt"

    // Factory methods
    // a one-arg constructor
    def apply(runType: Int): Day04 = {
        new Day04("AoC", runType)
    }

    // a two-arg constructor
    def apply(title: String, runType: Int): Day04 = {
        new Day04(title, runType)
    }

    private def runPuzzle(runType: Int): Unit = {

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
        // code goes here ...

        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()

        // each Card has a number N, Set of winning numbers, Set of my numbers
        case class Card (N: Int, wins: Set[Int], nums: Set[Int])

        val parseCardRE = raw"Card (\d+) : ([\d ]+)|([\d ]+)".r

        def buildScratchOffCards(input: Vector[String]): List[Card] = {
            input
                .map(x => parseCardRE.findAllIn(x).toVector)
                .map(x =>
                        Card( x(0).trim.toInt,
                              x(1).trim.split(" +").toSet.map(_.toInt),
                              x(2).trim.split(" +").toSet.map(_.toInt) ))
                .toList
        }

        val cards = buildScratchOffCards(input)

        val answerP1 = cards.map(card => Math.pow(2,card.nums.intersect(card.wins).size-1).toInt).sum

        println(s"Part 1: How many points are they worth in total?  A: $answerP1")
        val delta1 = Duration.between(p1T0, Instant.now())
        println(s"Part 1 run time approx ${delta1.toMillis} milliseconds\n")


        // ----------
        //  Part Two
        // ----------
        val p2T0 = Instant.now()

//        def process(card: Card, level: Int): Int = {
//            val winners = card.nums.intersect(card.wins)
//            var sum = 0
//            for i <- 0 to winners.size do
//                if card.N + i < cards.length then
//                    println(s"Process: Level $level: ${cards(card.N + i)}")
//                    sum = 1 + sum + process(cards(card.N + i), level+1)
//            sum
//        }

        var newCards:BigInt = 0
        val s = scala.collection.mutable.Stack[Int]()
        for card <- cards do
            val winners = card.nums.intersect(card.wins)
            //newCards += winners.size
            for i <- 0 until winners.size do
                if card.N+i < cards.length then
                    s.push(card.N+i)
                    //println(cards(card.N+i))
                    newCards += 1
            end for
            while s.nonEmpty do
                val thisCard = s.pop()
                val winners = cards(thisCard).nums.intersect(cards(thisCard).wins)
                //newCards += winners.size
                for i <- 0 until winners.size do
                    val nextCard = cards(thisCard).N + i
                    if nextCard < cards.length then
                        s.push(nextCard)
                        //println(nextCard)
                        newCards += 1
            end while
        end for


        val answerP2 = newCards + cards.size

        println(s"Part 2: Including the original set of scratchcards, how many total scratchcards do you end up with? A: $answerP2")
        val delta2 = Duration.between(p2T0, Instant.now())
        println(f"Part 2 run time approx ${delta2.toMillis} milliseconds")

        // errata...for visualization with Excel chart

    }

end Day04
