import scala.io.Source
import java.time.{Duration, Instant}
import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, Map}

/** Advent of Code 2023 Day 25
 *
 * Defines a class, its companion object and a runner method for
 * the AoC Day 25 puzzles.
 *
 * Created 25 Dec 2023
 * Link: https://adventofcode.com/2023/day/25
 */

class Day25 private (val title: String, val runType: Int ):

    //var title: String = "Advent of Code 2023"
    //var runType: Int = 1 // Default to test data
    def run():Unit = Day25.solution(runType)

    override def toString: String = s"Class ${Day25.puzzleTitle}"

end Day25

object Day25:

    val day = "25"
    val puzzleTitle = "Day 25: Snowverload"

    // input data files
    private val testData: String = s"$day-test.txt"
    private val realData: String = s"$day-input.txt"

    // Factory methods
    // a one-arg constructor
    def apply(runType: Int): Day25 = {
        new Day25("AoC", runType)
    }

    // a two-arg constructor
    def apply(title: String, runType: Int): Day25 = {
        new Day25(title, runType)
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

        case class Component(descr: String, isConnectedTo: List[String])

        val wd: List[Component] =
            input
                .map(x => x.split(":"))
                .map(x =>
                    Component(x(0).trim,
                        x(1).trim.split(" +").toList))
                .toList
        wd.foreach(println)

        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()

        // following algo to find bridges between connected components
        // presented by William Fiset on Udemy [Graph Theory Algorithms](https://www.udemy.com/course/graph-theory-algorithms/)
        // wd = the graph, a list of connected nodes of type Component

        // Determine the size of the graph
        // ??? how many nodes are there?
        // ! a bit clumsy but I'll add all the nodes to a mutable Map
        val nodes = mutable.Map[String,Int]()
        wd.foreach(x => {
            if ! nodes.contains(x.descr) then
                nodes += (x.descr -> 0)
            x.isConnectedTo.foreach(y => nodes += (y -> 0))
            })

        // and use the mutable Map to give each node an ID
        // doesn't matter which nodes gets which ID ???
        var z = 0
        for node <- nodes.keys do
            nodes(node) = z
            z += 1

        // Count up number of nodes
        val n = nodes.size
        nodes.foreach(println)

        // all will be of size n
        // in each ArrayBuffer the index is the ID of of the node as assigned above
        val ids = ArrayBuffer[Int]()
        val low = ArrayBuffer[Int]()
        val visited = ArrayBuffer[Boolean]()
        for node <- nodes do
            visited += false
            ids += 0
            low += 0

        var id = 0
        def dfs(at: Int, parent:Int, bridges:ArrayBuffer[Int]): Unit = {
            visited(at) = true
            id += 1
            ids(at) = id
            low(at) = ids(at)

            for to <- wd(nodes(at)).isConnectedTo do
                if to == parent then
                    continue

                if !visited(to) then
                    dfs(to, at, bridges)
                    low(at) = min(low(at), low(to))
                    if ids(at) < low(to) then
                        bridges += at
                        bridges += to
                    else
                        low(at) = min(low(at), ids(to))
        }

        def findBridges(): ArrayBuffer[Int] = {
            val bridges = ArrayBuffer[Int]()
            for i <- 0 to n do
                if !visited(i) then
                    dfs(i, -1, bridges)
            bridges
        }









        val answerP1 = 0
        println(s"Part 1: Find the three wires you need to disconnect in order to divide the components into two separate groups.")
        println(s"What do you get if you multiply the sizes of these two groups together?  A: $answerP1")

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

end Day25
