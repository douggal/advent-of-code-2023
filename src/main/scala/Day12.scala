import scala.io.Source
import java.time.{Duration, Instant}

/** Advent of Code 2023 Day 12
 *
 * Defines a class, its companion object and a runner method for
 * the AoC Day 12 puzzles.
 *
 * Created 12 Dec 2023
 * Link: https://adventofcode.com/2023/day/12
 */

class Day12 private (val title: String, val runType: Int ):

    //var title: String = "Advent of Code 2023"
    //var runType: Int = 1 // Default to test data
    def run():Unit = Day12.solution(runType)

    override def toString: String = s"Class ${Day12.puzzleTitle}"

end Day12

object Day12:

    val day = "12"
    val puzzleTitle = "Day 12: Hot Springs"

    // input data files
    private val testData: String = s"$day-test.txt"
    private val realData: String = s"$day-input.txt"

    // Factory methods
    // a one-arg constructor
    def apply(runType: Int): Day12 = {
        new Day12("AoC", runType)
    }

    // a two-arg constructor
    def apply(title: String, runType: Int): Day12 = {
        new Day12(title, runType)
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

        val input = Source.fromResource(filename).getLines().toList

        println("\nData Quality Control:")
        println(s"  Input file name: $filename")
        println(s"  Each line is a: ${input.head.getClass}")
        println(s"  Number lines: ${input.length}")
        println(s"  First line:  ${input.head}")
        println(s"  Last line:  ${input.last}")
        println

        // ----------------------
        //  Common to both parts
        // ----------------------
        // code goes here ...

        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()





        val answerP1 = 0
        println(s"Part 1: For each row, count all of the different arrangements of operational and broken")
        println(s"springs that meet the given criteria. What is the sum of those counts?  A: $answerP1")

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

end Day12
