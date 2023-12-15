import scala.io.Source
import java.time.{Duration, Instant}
import scala.annotation.tailrec

/** Advent of Code 2023 Day 14
 *
 * Defines a class, its companion object and a runner method for
 * the AoC Day 14 puzzles.
 *
 * Created 14 Dec 2023
 * Link: https://adventofcode.com/2023/day/14
 */

class Day14 private (val title: String, val runType: Int ):

    //var title: String = "Advent of Code 2023"
    //var runType: Int = 1 // Default to test data
    def run():Unit = Day14.solution(runType)

    override def toString: String = s"Class ${Day14.puzzleTitle}"

end Day14

object Day14:

    val day = "14"
    val puzzleTitle = "Day 14: Parabolic Reflector Dish"

    // input data files
    private val testData: String = s"$day-test.txt"
    private val realData: String = s"$day-input.txt"

    // Factory methods
    // a one-arg constructor
    def apply(runType: Int): Day14 = {
        new Day14("AoC", runType)
    }

    // a two-arg constructor
    def apply(title: String, runType: Int): Day14 = {
        new Day14(title, runType)
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
        //  Context
        // ----------------------

        val platform =
            input
                .map(x => x.toCharArray)
                .toList

        val maxRow = platform.length - 1
        val maxCol = platform.head.length - 1

        @tailrec
        def rollNorth(r: Int, c:Int): Unit = {
            // roll North
            if r > maxCol || r < 0 then
                ()
            else if platform(r)(c) == '#' || platform(r)(c) == '.' then
                rollNorth(r+1, c)
            else
                // round rock
                var prev = r - 1
                var curr = r
                while prev >= 0 && platform(prev)(c) == '.' do
                    platform(prev)(c) = 'O'
                    platform(curr)(c) = '.'
                    curr = prev
                    prev -= 1
                end while
                rollNorth(r + 1, c)
        }

        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()

        for c <- platform.head.indices do
            rollNorth(0, c)

        platform.foreach(x => println(x.mkString(" ")))

        var sum = 0
        for r <- platform.indices do
            for c <- platform.head.indices do
                if platform(r)(c) == 'O' then
                    sum += maxCol - r + 1

        val answerP1 = sum
        println(s"Part 1: Tilt the platform so that the rounded rocks all roll north.")
        println(s" Afterward, what is the total load on the north support beams?  A: $answerP1")

        val delta1 = Duration.between(p1T0, Instant.now())
        println(s"Run time approx ${delta1.toMillis} milliseconds\n")


        // ----------
        //  Part Two
        // ----------
        val p2T0 = Instant.now()



        val answerP2 = 0
        println(s"Part 2: TBD ???  A: $answerP2")

        val delta2 = Duration.between(p2T0, Instant.now())
        println(f"Run time approx ${delta2.toMillis} milliseconds")

        // errata...for visualization with Excel chart

    }

end Day14
