import scala.io.Source
import java.time.{Duration, Instant}
import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, Map}
import fansi.*

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
        // val filename = "25-test-2.txt"
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

        case class Component(descr: String, isConnectedTo: List[String])

        val wd: List[Component] =
            input
                .map(x => x.split(":"))
                .map(x =>
                    Component(x(0).trim,
                        x(1).trim.split(" +").toList))
                .toList
        // wd.foreach(println)

        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()

        // following algo to find bridges between connected components
        // presented by William Fiset on Udemy [Graph Theory Algorithms](https://www.udemy.com/course/graph-theory-algorithms/)
        // Also here: https://algs4.cs.princeton.edu/41graph/Bridge.java.html
        // And here: https://github.com/williamfiset/Algorithms/blob/master/src/main/java/com/williamfiset/algorithms/graphtheory/BridgesAdjacencyList.java
        // wd = the graph, a list of connected nodes of type Component

        // ??? Refactor the graph
        // to make the algo work, need all the nodes with what each node is connected to

        // ! a bit clumsy but I'll add all the nodes to a mutable Map
        val nodes = mutable.Map[String,mutable.Set[String]]()
        // Go thru the input data
        // Build an adjacency list - list of nodes in the graph
        // and to what each node is connected to
        // In the input data, for each node found, build up what it is connected to
        wd.foreach(x => {
            // look at each component, its key is connected to 1 or more nodes
            // if component not already in Map, add it
            if ! nodes.contains(x.descr) then
                nodes += (x.descr -> mutable.Set[String]())
            // add all the nodes it's connected to
            for y <- x.isConnectedTo do
                nodes(x.descr) += y
                // each node in the isConnectedTo list, check and add
                // reverse direction to the Map.  It's a Set no need to check if exist or not already
                if ! nodes.contains(y) then
                    nodes += (y -> mutable.Set[String]())
                nodes(y) += x.descr
            })

        // ??? How to give each node and ID?
        // ??? does it matter which nodes gets which ID ???
        // ! make a another map to translate each 3-char string description to an ID
        // toID - given ID find 3-char string node description
        // toNode - given 3-char node description find ID
        val toID = mutable.Map[String, Int]()
        val toNode = mutable.Map[Int, String]()
        var z = 0
        for node <- nodes.keys do
            toID(node) = z
            toNode(z) = node
            z += 1

        // Count up number of nodes
        val n = nodes.size

        println(s"The graph:")
        nodes.foreach(println)

        println(s"\nNumber of vertices: ${nodes.size}")

        println(s"The nodes with small nbr connections:")
        nodes.filter(x => x._2.size >= 5).foreach(println)

//        toID.foreach(println)
//        toNode.foreach(println)

        // all will be of size n
        // in each ArrayBuffer the index is the ID of of the node as assigned above
        val ids = ArrayBuffer[Int]()
        val low = ArrayBuffer[Int]()
        val visited = ArrayBuffer[Boolean]()
        for x <- nodes.values do
            visited += false
            ids += 0
            low += 0

        var id = 0
        def dfs(at: Int, parent:Int, bridges:ArrayBuffer[Int]): Unit = {
            visited(at) = true
            id += 1
            ids(at) = id
            low(at) = ids(at)

            // from each edge from node "at" to node "to"
            for to <- nodes(toNode(at)) do
                val too = toID(to)
                if too != parent then
                    if ! visited(too) then
                        dfs(too, at, bridges)
                        low(at) = Math.min(low(at), low(too))
                        if ids(at) < low(too) then
                            bridges += at
                            bridges += too
                        end if
                    else
                        low(at) = Math.min(low(at), ids(too))
                    end if
                end if
            end for
        }

        def findBridges(): ArrayBuffer[Int] = {
            val bridges = ArrayBuffer[Int]()
            // for each node by its ID in nodes Map
            for node <- toNode.keys.toList.sorted do
                if ! visited(node) then
                    dfs(node, -1, bridges)
            bridges
        }

        // Expect bridges to have 6 values in it ???
        val bridges = findBridges()

        println(s"\nNumber Bridges: ${bridges.length/2}")
        bridges.grouped(2).foreach(x => println(s"${toNode(x(0))} -  ${toNode(x(1))}"))

        val answerP1 = 0
        println(s"\nPart 1: Find the three wires you need to disconnect in order to divide the components into two separate groups.")
        println(s"What do you get if you multiply the sizes of these two groups together?  A: $answerP1")

        val delta1 = Duration.between(p1T0, Instant.now())
        println(s"Run time approx ${delta1.toMillis} milliseconds\n")


        // fansi
        val colored: fansi.Str = fansi.Color.Red("Hello World Ansi!")
        // Or fansi.Str("Hello World Ansi!").overlay(fansi.Color.Red)

        val length = colored.length // Fast and returns the non-colored length of string

        val blueWorld = colored.overlay(fansi.Color.Blue, 6, 11)

        val underlinedWorld = colored.overlay(fansi.Underlined.On, 6, 11)

        val underlinedBlue = blueWorld.overlay(fansi.Underlined.On, 4, 13)
        println(colored)
        println(blueWorld)
        println(underlinedWorld)
        println(underlinedBlue)

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
