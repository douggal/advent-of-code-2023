import scala.io.Source
import java.time.{Duration, Instant}

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

        /*
        with open(fn) as f:
            for line in f:
                if line.strip() != "":
                    if line[0:5] == "Time:":
                        time_list = re.split(' +', line[5:].strip())
                        # print(time_list)

                    if line[0:9] == "Distance:":
                        dist_list = re.split(' +', line[9:].strip())
                        # print(dist_list)

        # Part 1
        start_time_p1 = time.time()

        ts = list(map(int, time_list))
        ds = list(map(int, dist_list))
         */
        // list of run times (1st line of input)
        val ts = input.head.substring(7).trim.split(" +").map(x => x.trim.toInt).toList

        // list of distance records (2nd line of input)
        val ds = input.last.substring(9).trim.split(" +").map(x => x.trim.toInt).toList

        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()


        val answerP1 = 0
        println(s"Part 1: Determine the number of ways you could beat the record in each race. ")
        println(s"What do you get if you multiply these numbers together?  A:  $answerP1")

        val delta1 = Duration.between(p1T0, Instant.now())
        println(s"Part 1 run time approx ${delta1.toMillis} milliseconds\n")


        // ----------
        //  Part Two
        // ----------
        val p2T0 = Instant.now()



        val answerP2 = 0
        println(s"Part 2: How many ways can you beat the record in this one much longer race?  A: $answerP2")
        val delta2 = Duration.between(p2T0, Instant.now())
        println(f"Part 2 run time approx ${delta2.toMillis} milliseconds")

        // errata...for visualization with Excel chart

    }

end Day06
