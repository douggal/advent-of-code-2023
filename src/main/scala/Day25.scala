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
 *
 * 2nd try 31 May 2024
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

        // an Node class - connects 0 or more other nodes
        case class Node(descr: String, isConnectedTo: List[String])

        // read raw input and create an initial (incomplete) graph
        val inputgraph: List[Node] =
            input
                .map(x => x.split(":"))
                .map(x => Node(x(0).trim, x(1).trim.split(" +").toList))
                .toList

        // observe what was read in:
        // inputgraph.foreach(println)

        // Refactor the raw input graph
        //   Q: The input data does not have a row for every node!
        //       A complete graph will need all the nodes to appear in the list
        //       with any edges node touches.
        //   A: I'll add all the nodes to a mutable ArrayBuffer

        // G is full graph in adj. list form
        val G = mutable.Map[String, mutable.ArrayBuffer[String]]()

        // first pass - populate with given nodes
        inputgraph.foreach(n => { G += (n.descr -> n._2.to(ArrayBuffer)) })

        // 2nd pass - pick up any nodes not already in G
        for node <- G do
             for n <- node._2 do  // each node this node is connected to
                if !G.contains(n) then
                    G += (n -> mutable.ArrayBuffer[String]())
                if !G(n).contains(node._1) then
                    G(n) += node._1

        // Q: The algos often use small integers to label the nodes
        //    and the small ints are used to index arrays.
        //    How can I label the nodes as integers?
        // A: Make 2 maps to translate each 3-char string description to an ID and vice versa
        // toID - assign and ID number to each 3 char description
        // toNode - revers toID - given ID map to node char description
        val toID = G.keys.zipWithIndex.toMap
        val toNode = toID.map(x => (x._2, x._1))
        // toID.foreach(println)
        // toNode.foreach(println)

        println("Completed reading input and building graph.")
        println(s"The graph has ${G.size} nodes.")
        println("The graph as adj list:")
        G.foreach(println)
        println

        // Make a deep copy of G
        // https://stackoverflow.com/questions/9049117/copy-contents-of-immutable-map-to-new-mutable-map/9049302
        val Gprime = mutable.Map[String,mutable.ArrayBuffer[String]]() ++ G

        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()

        // Try 1
        // Q: are there any obvious partitions around nodes with small number of connections ?
        // A: no
        // println(s"The nodes with small nbr connections:")
        // G.filter(x => x._2.size >= 5).foreach(println)
        // End Try 1

        // Try 2
        // Q: are there any bridges - graph components with only one edge between them?
        // A: try to find bridges if they exist.

        // in each ArrayBuffer the index is the ID of of the node as assigned above
        val ids = ArrayBuffer[Int]()
        val low = ArrayBuffer[Int]()
        val visited = ArrayBuffer[Boolean]()
        for x <- G.values do
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
            for to <- G(toNode(at)) do
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

        val bridges = findBridges()

        println(s"\nNumber Bridges: ${bridges.length/2}")
        bridges.grouped(2).foreach(x => println(s"${toNode(x(0))} -  ${toNode(x(1))}"))

        if bridges.isEmpty then
            println("Outcome:  no go, there are no bridges in this graph")

        // End Try 2

        // Try 3
        // 31 May 2024 - Try Karger's Algorithm

        /* Karger's algo, as I understand it, applied here:
            We know solution exists and min cut == 3 edges
            Divide graph G into two components, S and T by merging together nodes chosen at random
            Count edges connecting S and T
            When # edges == 3 then we're done

           found = false
           i = 0
            while not found and i < 1e6
                i += 1
                while G.size > 2
                   tqke 2 nodes at random from graph G
                   collapse the selected nodes
                if  # edges between S, T is == 3
                    found = true
         */

        val S = mutable.ArrayBuffer[String]()
        val T = mutable.ArrayBuffer[String]()
        var found = false
        var i = 0
        while !found && i < 1e6 do
            i += 1
            var n1 = ""
            var n2 = ""
            while G.size > 2 do
                // https://stackoverflow.com/questions/34817917/how-to-pick-a-random-value-from-a-collection-in-scala
                n1 = G.maxBy(_=> scala.util.Random.nextInt)._1
                n2 = G.maxBy(_=> scala.util.Random.nextInt)._1
                if n1 != n2 then
                    // coalesce()
                    // take all n2's edges and assign to n1
                    for e <- G(n2) do
                        if !G(n1).contains(e) then
                            G(n1) += e
                    // delete n2 from G
                    G -= n2
            end while
            // how do I count edges between the two groups ???
            found = true
        end while

        // assert G has only 2 nodes left
        if G.size == 2 then
            println("Works")
            for e <- G.keys do
                println(s"Size of element: ${G(e).length}")



        val answerP1 = 0
        println(s"\nPart 1: Find the three wires you need to disconnect in order to")
        println(s"divide the components into two separate groups.")
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

        // errata...
        // test fansi console output text coloring:
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


    }

end Day25
