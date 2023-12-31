import scala.io.Source
import java.time.{Duration, Instant}
import scala.collection.mutable.{ArrayBuffer}

/** Advent of Code 2023 Day 06
 *
 * Defines a class, its companion object and a runner method for
 * the AoC Day 06 puzzles.
 *
 * Created 6 Dec 2023
 * Link: https://adventofcode.com/2023/day/6
 */

class Day06 private (val title: String, val runType: Int ):

    //var title: String = "Advent of Code 2023"
    //var runType: Int = 1 // Default to test data
    def run():Unit = Day06.runPuzzle(runType)

    override def toString: String = s"Class ${Day06.puzzleTitle}"

end Day06

object Day06:

    val day = "06"
    val puzzleTitle = "Day 6: Wait For It"

    // input data files
    private val testData: String = s"$day-test.txt"
    private val realData: String = s"$day-input.txt"

    // Factory methods
    // a one-arg constructor
    def apply(runType: Int): Day06 = {
        new Day06("AoC", runType)
    }

    // a two-arg constructor
    def apply(title: String, runType: Int): Day06 = {
        new Day06(title, runType)
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

        // list of run times (1st line of input)
        val ts = input.head.substring(7).trim.split(" +").map(x => x.trim.toInt).toList

        // list of distance records (2nd line of input)
        val ds = input.last.substring(9).trim.split(" +").map(x => x.trim.toInt).toList

        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()

        val ws = ArrayBuffer[Int]()
        for (t, rd) <- ts zip ds do
            // println("Race:", t, rd)
            var ways_to_win = 0
            for h <- 0 to t do
                val d = (t - h) * h
                if d > rd then
                    // print("Win! time, hold, rate, dist", t, h, h, d)
                    ways_to_win += 1
            ws += ways_to_win

        val answerP1 = ws.product
        println(s"Part 1: Determine the number of ways you could beat the record in each race. ")
        println(s"What do you get if you multiply these numbers together?  A:  $answerP1, Nbr of wins ${ws.sum}")

        val delta1 = Duration.between(p1T0, Instant.now())
        println(s"Part 1 run time approx ${delta1.toMillis} milliseconds\n")


        // ----------
        //  Part Two
        // ----------
        val p2T0 = Instant.now()

        val ts2 = BigInt(input.head.substring(7).split("\\s+").toList.mkString.toLong)
        val ds2 = BigInt(input.last.substring(10).split("\\s+").toList.mkString.toLong)
        val ws2 = ArrayBuffer[BigInt]()
        var ways_to_win = 0L
        var h = BigInt(0)
        while h <= ts2 do
            val d = (ts2 - h) * h
            if d > ds2 then
                // print("Win! time, hold, rate, dist", t, h, h, d)
                ways_to_win += 1
            h += 1
        ws2 += ways_to_win

        val answerP2 = ws2(0)
        println(s"Part 2: How many ways can you beat the record in this one much longer race?  A: $answerP2, Nbr of wins ${ws2(0)}")
        val delta2 = Duration.between(p2T0, Instant.now())
        println(f"Part 2 run time approx ${delta2.toMillis} milliseconds")

        // errata...for visualization with Excel chart

    }

end Day06
