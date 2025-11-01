import scala.io.Source
import java.time.{Duration, Instant}
import scala.collection.mutable.{ArrayBuffer, Queue}

/** Advent of Code 2023 Day 10
 *
 * Defines a class, its companion object and a runner method for
 * the AoC Day 10 puzzles.
 *
 * Created 10 Dec 2023
 * Link: https://adventofcode.com/2023/day/10
 */

class Day10 private (val title: String, val runType: Int ):

    //var title: String = "Advent of Code 2023"
    //var runType: Int = 1 // Default to test data
    def run():Unit = Day10.solution(runType)

    override def toString: String = s"Class ${Day10.puzzleTitle}"

end Day10

object Day10:

    val day = "10"
    val puzzleTitle = "Day 10: "

    // input data files
    private val testData: String = s"$day-test.txt"
    private val realData: String = s"$day-input.txt"

    // Factory methods
    // a one-arg constructor
    def apply(runType: Int): Day10 = {
        new Day10("AoC", runType)
    }

    // a two-arg constructor
    def apply(title: String, runType: Int): Day10 = {
        new Day10(title, runType)
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
        // represent pipe maze as an adjacency list
        // in a 1-D list of tiles (nodes)
        // Each tile has an ID, the index of its position in the list, a face symbol, and
        // a List of nodes to which it is connected/adjacent
        // The degree of each node is given by count of connected nodes
        val symbols = ("SFL-|7J").toCharArray.toSet
        val maxRow = input.length
        val maxCol = input.head.count(_ => true)

        // clockwise from top, no diagonals, Tuple is (row, column), and orientation is y/row-down
        val neighbors = List((-1, 0), (0, 1), (1, 0), (0, -1))
        val neighborsF = List((0,1),(1,0))
        val neighbors7 = List((0,-1),(1,0))
        val neighborsJ = List((-1,0),(0,-1))
        val neighborsL = List((-1,0),(0,1))
        val neighborsPipe = List((-1, 0),(1,0))
        val neighborsDash = List((0,-1),(0,1))

        def toIndex(r: Int, c: Int): Int = {
            // convert row and column to index in the grid
            r * maxCol + c
        }

        def getOneNeighbor(rr: Int, cc: Int): Option[(Int,Char)] = {
            if rr >= 0 && rr < maxRow && cc >= 0 && cc < maxCol && input(rr)(cc) != '.' then
                Option((toIndex(rr, cc), input(rr)(cc)))
            else
                None
        }

        def getNeighbors(r: Int, c: Int, symbol: Char): List[Int] = {
            val ns = ArrayBuffer[Int]() // this tiles neighbors

            // where to look?  depends on what symbol this tile contains
            symbol match
                case 'S' => {
                    // special case - cover all 8 neighbors, S can connect to only 2 of them
                    for t <- neighbors do
                        val x = getOneNeighbor(r + t._1, c + t._2)
                        x match
                            case Some(value) => {
                                // up, down, right, left
                                val face = value._2
                                if t._1 == -1 && (face == '|' || face == 'F'|| face == '7') then
                                    ns += value._1
                                if t._1 == 1 && (face == '|' || face == 'L' || face == 'J') then
                                    ns += value._1
                                if t._2 == 1 && (face == '-' || face == 'J' || face == '7') then
                                    ns += value._1
                                if t._2 == -1 && (face == 'L' || face == '-' || face == 'F') then
                                    ns += value._1
                            }
                            case None => () /* no action */
                    if ns.length > 2 || ns.length == 0 then
                        println(s"Error - bad S tile: ($r, $c) ")
                }
                case 'F' => {
                    // right, and down
                    for t <- neighborsF do
                        val x = getOneNeighbor(r + t._1, c + t._2)
                        x match
                            case Some(value) => ns += value._1
                            case None => ()
                }
                case '-' => {
                    // right, left
                    for t <- neighborsDash do
                        val x = getOneNeighbor(r + t._1, c + t._2)
                        x match
                            case Some(value) => ns += value._1
                            case None => () /* no action */
                }
                case '|' => {
                    // top bottom
                    for t <- neighborsPipe do
                        val x = getOneNeighbor(r + t._1, c + t._2)
                        x match
                            case Some(value) => ns += value._1
                            case None => () /* no action */
                }
                case '7' => {
                    // down and left
                    for t <- neighbors7 do
                        val x = getOneNeighbor(r + t._1, c + t._2)
                        x match
                            case Some(value) => ns += value._1
                            case None => () /* no action */
                }
                case 'J' => {
                    // top and left
                    for t <- neighborsJ do
                        val x = getOneNeighbor(r + t._1, c + t._2)
                        x match
                            case Some(value) => ns += value._1
                            case None => () /* no action */
                }
                case 'L' => {
                    // top and right
                    for t <- neighborsL do
                        val x = getOneNeighbor(r + t._1, c + t._2)
                        x match
                            case Some(value) => ns += value._1
                            case None => () /* no action */
                }
                case _ => () /* no action */

            ns.toList
        }

        case class Tile(id: Int, face: Char, isConnectedTo: List[Int])

        val pipeMaze = ArrayBuffer[Tile]()
        for li <- input.zipWithIndex do
            for c <- li._1.zipWithIndex do
                val i = toIndex(li._2,c._2)
                val ns = getNeighbors(li._2,c._2,c._1)
                if ns.length > 0 then
                    pipeMaze += Tile(i,c._1,ns)
                else
                    pipeMaze += Tile(i,c._1, List())

        // pipeMaze.foreach(x => println(s"Tile ${x.id} has Face ${x.face}, and is Connected to Tiles: ${x.isConnectedTo.mkString(",")}"))

        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()

        /*
        If you want to get out ahead of the animal, you should find the tile in the loop that is farthest from
        the starting position. Because the animal is in the pipe, it doesn't make sense to measure this by
        direct distance. Instead, you need to find the tile that would take the longest number of steps
        along the loop to reach from the starting point - regardless of which way around the loop the animal went.
         */

        //  What to do ???
        //  idea ! DFS
        /*
            https://en.wikipedia.org/wiki/Depth-first_search
            procedure DFS(G, v) is
                label v as discovered
                for all directed edges from v to w that are in G.adjacentEdges(v) do
                    if vertex w is not labeled as discovered then
                        recursively call DFS(G, w)
         */

//        // depth of each tile from starting tile
//        val depths = ArrayBuffer[Int]()
//
//        // keep track of which tiles have be processed
//        val discovered = ArrayBuffer[Boolean]()
//
//        // intialize
//        val startTile = pipeMaze.filter(x => x.face == 'S').head.id
//        for i <- 0 until (maxCol * maxRow) do
//            discovered += false
//            depths += 0
//
    //        // Depth First Search from starting tile S to
    //        // each connected tile in the pipe maze diagram
    //        // this traversal works, but I have no way to know when I'm at the furthest # steps from start
    //        var currLevel = 0
    //        def DFS(G: ArrayBuffer[Tile], v: Int): Unit = {
    //            println(s"Visiting tile $v, level $currLevel")
    //            discovered(v) = true
    //            depths(v) += currLevel
    //            currLevel += 1
    //            for tile <- G(v).isConnectedTo do
    //                if !discovered(tile) then
    //                    DFS(G,tile)
    //                end if
    //            end for
    //            currLevel = currLevel - 1
    //            ()
    //        }
    //
    //        println("DFS")
    //        DFS(pipeMaze, startTile)
//        val mostStepsDFS = depths.max


        // -----------------------------------------------------
        // DFS did not give needed answer. What to do next ???
        //  idea ! BFS
        // -----------------------------------------------------

        def BFS(s: Int, e: Int): ArrayBuffer[Int] = {
            // prev -  is each node's parent (index gives node ID)
            val prev = ArrayBuffer[Int]()

            // keep track of which tiles have be processed
            val discoveredBFS = ArrayBuffer[Boolean]()

            // initialize parent of each node to empty string
            for i <- 0 until maxCol * maxRow do
                prev += -1
                discoveredBFS += false

            // Queue to store tiles awaiting processing
            val q = Queue[Int](s)

            while !q.isEmpty do
                val tile = q.dequeue()
                discoveredBFS(tile) = true
                // println(s"Visited tile $tile")

                // add all node's neighbors to the the queue, if not already there
                for neighbor <- pipeMaze(tile).isConnectedTo do

                    // ignore n if already explored
                    // else add to queue
                    if discoveredBFS(neighbor) == false then
                        discoveredBFS(neighbor) = true
                        q.enqueue(neighbor)
                        prev(neighbor) = tile // n's parent is "node"
                    end if
                end for
            end while

            prev
        }

        def reconstructPath(s: Int, e: Int, prev: ArrayBuffer[Int]): ArrayBuffer[Int] = {
            val path = ArrayBuffer[Int]()
            var at = e
            while at != -1 do
                path += at
                at = prev(at)

            val pathr = path.reverse

            if pathr(0) == s then
                pathr
            else
                ArrayBuffer[Int]()
        }

        def solve(s: Int, e: Int): ArrayBuffer[Int] = {
            // do a BSF of g starting at s
            val prev = BFS(s, e)
            // return reconstructed path s -> e
            reconstructPath(s, e, prev)
        }

        // Run BFS from Start tile to all possible end tiles
        println("\n\nBFS:")
        val startTile = pipeMaze.filter(x => x.face == 'S').head.id
        val paths = ArrayBuffer[ArrayBuffer[Int]]()
        for endTile <- 0 until maxCol*maxRow do
            if startTile != endTile && pipeMaze(endTile).face != '.' then
                val ps = solve(startTile, endTile)
                if ps.length > 0 then
                    paths += ps
        end for

        // paths.foreach(x => {if x.length > 0 then println(s"Start: path: ${x.mkString(",")}")})

        val mostStepsBFS = paths.map(_.length).max - 1

        // no go 6932 is too low

        val answerP1 = mostStepsBFS
        println(s"Part 1: ")
        println(s"  A: $answerP1")

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

end Day10
