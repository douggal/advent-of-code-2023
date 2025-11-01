import scala.io.Source
import java.time.{Duration, Instant}
import scala.collection.mutable.{ArrayBuffer}

/** Advent of Code 2023 Day 9
 *
 * Defines a class, its companion object and a runner method for
 * the AoC Day 09 puzzles.
 *
 * Created 9 Dec 2023
 * Link: https://adventofcode.com/2023/day/9
 */

class Day09 private (val title: String, val runType: Int ):

    //var title: String = "Advent of Code 2023"
    //var runType: Int = 1 // Default to test data
    def run():Unit = Day09.solution(runType)

    override def toString: String = s"Class ${Day09.puzzleTitle}"

end Day09

object Day09:

    val day = "09"
    val puzzleTitle = "Day 9: Mirage Maintenance"

    // input data files
    private val testData: String = s"$day-test.txt"
    private val realData: String = s"$day-input.txt"

    // Factory methods
    // a one-arg constructor
    def apply(runType: Int): Day09 = {
        new Day09("AoC", runType)
    }

    // a two-arg constructor
    def apply(title: String, runType: Int): Day09 = {
        new Day09(title, runType)
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

        val dataset = input.map(x => x.split(" +").map(_.toInt))
        // dataset.map(_.mkString(",")).foreach(println)

        val nextItemInEachHistory = ArrayBuffer[Int]()

        for history <- dataset do
            val diffs = ArrayBuffer[List[Int]]()
            diffs += history.sliding(2).map {
                case Array(a, b) => b - a
            }.toList
            var sum = Long.MaxValue
            while sum != 0 do
                diffs += diffs.last.sliding(2).map {
                    case List(a,b)=> b - a
                }.toList
                sum = diffs.last.sum
            end while

            val nextItems = ArrayBuffer[Int](0)
            for x <- diffs.reverse.drop(1) do
                nextItems += x.last + nextItems.last

            nextItemInEachHistory += nextItems.last + history.last

            // println("History:")
            // diffs.map(_.mkString(",")).foreach(println)
        end for


        
        
        val answerP1 = nextItemInEachHistory.sum
        println(s"Part 1: ")
        println(s" A: $answerP1")

        val delta1 = Duration.between(p1T0, Instant.now())
        println(s"Part 1 run time approx ${delta1.toMillis} milliseconds\n")


        // ----------
        //  Part Two
        // ----------
        val p2T0 = Instant.now()

        val firstItemInEachHistory = ArrayBuffer[Int]()

        for history <- dataset do
            val diffs = ArrayBuffer[List[Int]]()
            diffs += history.sliding(2).map {
                case Array(a, b) => b - a
            }.toList
            var sum = Long.MaxValue
            while sum != 0 do
                diffs += diffs.last.sliding(2).map {
                    case List(a, b) => b - a
                }.toList
                sum = diffs.last.sum
            end while

            val firstItems = ArrayBuffer[Int](0)
            for x <- diffs.reverse.drop(1) do
                firstItems += x.head - firstItems.last

            firstItemInEachHistory += history.head - firstItems.last

            // println("History:")
            // diffs.map(_.mkString(",")).foreach(println)
            // firstItemInEachHistory.foreach(println)
        end for


        val answerP2 = firstItemInEachHistory.sum
        println(s"Part 2:  ")
        println(s" A: $answerP2")

        val delta2 = Duration.between(p2T0, Instant.now())
        println(f"Part 2 run time approx ${delta2.toMillis} milliseconds")

        // errata...for visualization with Excel chart

    }

end Day09
