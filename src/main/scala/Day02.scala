import java.time.{Duration, Instant}
import scala.collection.mutable
import scala.io.Source
import scala.collection.mutable.{ArrayBuffer, Set}

/** Advent of Code 2023 Day 02
 *
 * Defines a class, its companion object and a runner method for
 * the AoC Day 2 puzzles.
 *
 * Created 2 Dec 2023
 * Link: https://adventofcode.com/2023/day/2
 */

class Day02 private (val title: String, val runType: Int ):

    //var title: String = "Advent of Code 2023"
    //var runType: Int = 1 // Default to test data
    def run():Unit = Day02.runPuzzle(runType)

    override def toString: String = s"Class ${Day02.puzzleTitle}"

end Day02

object Day02:

    val day = "02"
    val puzzleTitle = "--- Day 2: ---"

    // input data files
    private val testData: String = s"$day-test.txt"
    private val realData: String = s"$day-input.txt"

    // Factory methods
    // a one-arg constructor
    def apply(runType: Int): Day02 = {
        new Day02("AoC", runType)
    }

    // a two-arg constructor
    def apply(title: String, runType: Int): Day02 = {
        new Day02(title, runType)
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
        // RE to split out game # from data in each input line
        val gameHeaderRE = raw"(Game [0-9]+: )(.+)".r
        val colors = List("red", "green", "blue")

        // target numbers
        val redTarget = 12
        val greenTarget = 13
        val blueTarget = 14

        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()

        // Color count arrays.  Use parallel arrays, one each for red, green and blue
        // 0 based so game number will be -1
        // each index represents game #, and its Vector of Ints is counts from each draw
        val reds = ArrayBuffer[ArrayBuffer[Int]]()
        val greens = ArrayBuffer[ArrayBuffer[Int]]()
        val blues = ArrayBuffer[ArrayBuffer[Int]]()

        for li <- input do
            // peel off the game numbers from the line header
            @unchecked
            val gameHeaderRE(head, game) = li

            // split on ;
            // Ex:  3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
            val draws = game.split(";")

            // split on , and add number to each game's color count array
            // Ex 3 blue, 4 red
            reds += ArrayBuffer[Int]()
            greens += ArrayBuffer[Int]()
            blues += ArrayBuffer[Int]()
            for draw <- draws do
                val xs = draw.split(",").map(x => x.trim)
                for x <- xs do
                    for color <- colors do
                        if color == "red" && x.contains("red") then
                            reds.last += x.split(" ").head.trim.toInt
                        if color == "green" && x.contains("green") then
                            greens.last += x.split(" ").head.trim.toInt
                        if color == "blue" && x.contains("blue") then
                            blues.last += x.split(" ").head.trim.toInt

        // Data structures are built and populated, now answer question
        // return Game # of each possible game
        // pRed for example is the set of possible game #s meeting minimum condition Red count <= 12
        var pRed = mutable.Set[Int]()
        var pGreen = mutable.Set[Int]()
        var pBlue = mutable.Set[Int]()
        for r <- reds.zipWithIndex do
            if r._1.count(_ > redTarget) == 0 then
                pRed += r._2 + 1
        for r <- greens.zipWithIndex do
            if r._1.count(_ > greenTarget) == 0 then
                pGreen += r._2 + 1
        for r <- blues.zipWithIndex do
            if r._1.count(_ > blueTarget) == 0 then
                pBlue += r._2 + 1

        val answerVector = pRed.intersect(pGreen.intersect(pBlue)).toVector

        // answerVector.sortWith(_ < _).foreach(println)

        println(s"Part 1: Determine which games would have been possible if the bag had been loaded with only")
        println(s"12 red cubes, 13 green cubes, and 14 blue cubes. What is the sum of the IDs of those games?")
        println(answerVector.sum)

        val delta1 = Duration.between(p1T0, Instant.now())
        println(s"Part 1 run time approx ${delta1.toMillis} milliseconds\n")


        // ----------
        //  Part Two
        // ----------
        val p2T0 = Instant.now()

        val maxReds = ArrayBuffer[Int]()
        val maxGreens = ArrayBuffer[Int]()
        val maxBlues = ArrayBuffer[Int]()
        for r <- reds do
            maxReds += r.max
        for r <- greens do
            maxGreens += r.max
        for r <- blues do
            maxBlues += r.max

        val powerSet = ArrayBuffer[Int]()
        for i <- maxReds.indices do
            powerSet += maxReds(i) * maxGreens(i) * maxBlues(i)

        val answerP2 = powerSet.sum

        println(s"Part 2: For each game, find the minimum set of cubes that must have been present. ")
        println(s"What is the sum of the power of these sets?")
        println(answerP2)

        val delta2 = Duration.between(p2T0, Instant.now())
        println(f"Part 2 run time approx ${delta2.toMillis} milliseconds")

        // errata...for visualization with Excel chart

    }

end Day02
