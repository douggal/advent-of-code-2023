import scala.io.Source
import java.time.{Duration, Instant}
import scala.collection.mutable.ListBuffer

/** Advent of Code 2023 Day 8
 *
 * Defines a class, its companion object and a runner method for
 * the AoC Day 8 puzzles.
 *
 * Created 8 Dec 2023
 * Link: https://adventofcode.com/2023/day/8
 */

class Day08 private (val title: String, val runType: Int ):

    //var title: String = "Advent of Code 2023"
    //var runType: Int = 1 // Default to test data
    def run():Unit = Day08.solution(runType)

    override def toString: String = s"Class ${Day08.puzzleTitle}"

end Day08

object Day08:

    val day = "08"
    val puzzleTitle = "--- Day 8: Haunted Wasteland ---"

    // input data files
    private val testData: String = s"$day-test.txt"
    private val realData: String = s"$day-input.txt"

    // Factory methods
    // a one-arg constructor
    def apply(runType: Int): Day08 = {
        new Day08("AoC", runType)
    }

    // a two-arg constructor
    def apply(title: String, runType: Int): Day08 = {
        new Day08(title, runType)
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

        // List of Left Right instructions
        val leftRights = input(0).trim.map(_.toString).toVector

        // Parse input data and build the network as a Map strings to tuples: key -> (left, right)

        // Parse string using regex:
        // https://stackoverflow.com/questions/39747453/scala-string-pattern-matching-and-splitting
        // Parse each row of data
        def matchStr(str: String): Option[(String, (String, String))] = {
            //val regex = raw"([A-Z][A-Z][A-Z]) = \(([A-Z][A-Z][A-Z]), ([A-Z][A-Z][A-Z])\)".r.unanchored
            val regex = raw"([\w][\w][\w]) = \(([\w][\w][\w]), ([\w][\w][\w])\)".r.unanchored
            str match {
                case regex(a, b, c) => Some(a -> (b, c))
                case _ => None
            }
        }

        // extract the data from the Option wrapper
        def extract(maybeData: Option[(String, (String, String))]): (String, (String, String)) = {
            maybeData.getOrElse("Null" -> ("Null", "Null"))
        }

        // Could check for None / bad data
        // Note: this does not fail if there are duplicate keys
        val network = input.drop(2).map(x => matchStr(x)).map(x => extract(x)).toMap

        // View Network when using test data set:
        if runType == 1 then
            network.foreach(println)

        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()

        var steps = 0L
        var instrPtr = 0

        if false then  // skip for part 2 test data
            var node = "AAA"
            while node != "ZZZ" do
                val next = leftRights(instrPtr)
                node = if next == "L" then network(node)._1 else network(node)._2
                instrPtr = (instrPtr + 1) % leftRights.length
                steps += 1
                //println(s"$node $steps, $instrPtr")


        val answerP1 = steps
        println(s"Part 1: Starting at AAA, follow the left/right instructions. ")
        println(s"How many steps are required to reach ZZZ?  A: $answerP1")
        val delta1 = Duration.between(p1T0, Instant.now())
        println(s"Part 1 run time approx ${delta1.toMillis} milliseconds\n")


        // ----------
        //  Part Two
        // ----------
        val p2T0 = Instant.now()

        var nodesP2 = network.keys.filter(x => x.last == 'A').toList
        steps = 0
        instrPtr = 0
        while nodesP2.count(x => x.last != 'Z') > 0 do
            nodesP2 = if leftRights(instrPtr) == "L" then nodesP2.map(x => network(x)._1) else nodesP2.map(x => network(x)._2)
            instrPtr = (instrPtr + 1) % leftRights.length
            steps += 1
            if steps % 1e6 == 0 then println(s"Steps $steps")
            // println(s"$steps, $instrPtr, $next, $nodesP2")


        val answerP2 = steps
        println(s"Part 2: Simultaneously start on every node that ends with A. ")
        println(s"How many steps does it take before you're only on nodes that end with Z?  A: $answerP2")

        val delta2 = Duration.between(p2T0, Instant.now())
        println(f"Part 2 run time approx ${delta2.toMillis} milliseconds")

        // errata...for visualization with Excel chart

    }

end Day08
