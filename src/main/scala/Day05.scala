import scala.io.Source
import java.time.{Duration, Instant}
import scala.collection.mutable.ArrayBuffer

/** Advent of Code 2023 Day 5
 *
 * Defines a class, its companion object and a runner method for
 * the AoC Day 5 puzzles.
 *
 * Created 5 Dec 2023
 * Link: https://adventofcode.com/2023/day/5
 */

class Day05 private (val title: String, val runType: Int ):

    //var title: String = "Advent of Code 2023"
    //var runType: Int = 1 // Default to test data
    def run():Unit = Day05.runPuzzle(runType)

    override def toString: String = s"Class ${Day05.puzzleTitle}"

end Day05

object Day05:

    val day = "05"
    val puzzleTitle = "Day 5: If You Give A Seed A Fertilizer"

    // input data files
    private val testData: String = s"$day-test.txt"
    private val realData: String = s"$day-input.txt"

    // Factory methods
    // a one-arg constructor
    def apply(runType: Int): Day05 = {
        new Day05("AoC", runType)
    }

    // a two-arg constructor
    def apply(title: String, runType: Int): Day05 = {
        new Day05(title, runType)
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

        // read input file - create big string of all lines, preserve new lines
        val input = Source.fromResource(filename).getLines().mkString("\n")

        // println(input)

        println("\nData Quality Control:")
        println(s"  Input file name: $filename")
        println(s"  Each line is a: ${input.getClass}")
        println(s"  Number lines: ${input.filter(x => x == '\n').length + 1}")
        println(s"  First line: ${input.head}")
        if input.size > 1 then
            println(s"  Last line: ${input.last}")
        println

        // ----------------------
        //  Common to both parts
        // ----------------------

        case class mapEntry(dest: BigInt, src: BigInt, runLength: Int)

        val seedsRE = raw"(seeds: +)([\d ]*\n)".r
        val seed2soilMapRE = raw"(?s)(seed-to-soil map:\n)(.*?)(?:(?:\r*\n){2})".r
        val soil2fertMapRE = raw"(?s)(soil-to-fertilizer map:\n)(.*?)(?:(?:\r*\n){2})".r
        val fert2waterMapRE = raw"(?s)(fertilizer-to-water map:\n)(.*?)(?:(?:\r*\n){2})".r
        val water2lightMapRE = raw"(?s)(water-to-light map:\n)(.*?)(?:(?:\r*\n){2})".r
        val light2tempMapRE = raw"(?s)(light-to-temperature map:\n)(.*?)(?:(?:\r*\n){2})".r
        val temp2humidityMapRE = raw"(?s)(temperature-to-humidity map:\n)(.*?)(?:(?:\r*\n){2})".r
        val humidity2locationMapRE = raw"(?s)(humidity-to-location map:\n)(.*?)(?:(?:\r*\n){2})".r

        def buildSeedsList(input: String): List[BigInt] = {
            seedsRE
                .findAllIn(input)
                .matchData
                .map(_.group(2))
                .mkString.trim
                .split(" +")
                .map(BigInt(_))
                .toList
            // println(seedsRE.findAllIn(input).matchData.map(x => x.group(2)).mkString)
        }

        def buildSeedToSoilMap(input: String): List[mapEntry] = {
             soil2fertMapRE
                .findAllIn(input)
                .matchData
                .map(m => m.group(2)).mkString.trim.replace('\n',' ')
                .split(" +")
                .map(BigInt(_))
                .toList
                .grouped(3)
                .map(x => mapEntry(dest=x(0),src = x(1), runLength = x(2).toInt))
                 .toList
        }

        val seeds = buildSeedsList(input)
        //seeds.foreach(println)
        val seedsToSoil = seed2soilMapRE
            .findAllIn(input)
            .matchData
            .map(m => m.group(2)).mkString.trim.replace('\n', ' ')
            .split(" +")
            .map(BigInt(_))
            .toList
            .grouped(3)
            .map(x => mapEntry(dest = x(0), src = x(1), runLength = x(2).toInt))
            .toList

        val soil2fert = soil2fertMapRE
            .findAllIn(input)
            .matchData
            .map(m => m.group(2)).mkString.trim.replace('\n', ' ')
            .split(" +")
            .map(BigInt(_))
            .toList
            .grouped(3)
            .map(x => mapEntry(dest = x(0), src = x(1), runLength = x(2).toInt))
            .toList

        val fert2water = fert2waterMapRE
            .findAllIn(input)
            .matchData
            .map(m => m.group(2)).mkString.trim.replace('\n', ' ')
            .split(" +")
            .map(BigInt(_))
            .toList
            .grouped(3)
            .map(x => mapEntry(dest = x(0), src = x(1), runLength = x(2).toInt))
            .toList

        val water2light = water2lightMapRE
            .findAllIn(input)
            .matchData
            .map(m => m.group(2)).mkString.trim.replace('\n', ' ')
            .split(" +")
            .map(BigInt(_))
            .toList
            .grouped(3)
            .map(x => mapEntry(dest = x(0), src = x(1), runLength = x(2).toInt))
            .toList

        val light2temp = light2tempMapRE
            .findAllIn(input)
            .matchData
            .map(m => m.group(2)).mkString.trim.replace('\n', ' ')
            .split(" +")
            .map(BigInt(_))
            .toList
            .grouped(3)
            .map(x => mapEntry(dest = x(0), src = x(1), runLength = x(2).toInt))
            .toList

        val temp2humidity = temp2humidityMapRE
            .findAllIn(input)
            .matchData
            .map(m => m.group(2)).mkString.trim.replace('\n', ' ')
            .split(" +")
            .map(BigInt(_))
            .toList
            .grouped(3)
            .map(x => mapEntry(dest = x(0), src = x(1), runLength = x(2).toInt))
            .toList

        val humidity2location = humidity2locationMapRE
            .findAllIn(input)
            .matchData
            .map(m => m.group(2)).mkString.trim.replace('\n', ' ')
            .split(" +")
            .map(BigInt(_))
            .toList
            .grouped(3)
            .map(x => mapEntry(dest = x(0), src = x(1), runLength = x(2).toInt))
            .toList

//        seedsToSoil.foreach(println)
//        soil2fert.foreach(println)
//        fert2water.foreach(println)
//        water2light.foreach(println)
//        light2temp.foreach(println)
//        temp2humidity.foreach(println)
//        humidity2location.foreach(println)

        // ----------
        //  Part One
        // ----------
        val p1T0 = Instant.now()

        val locs = ArrayBuffer[BigInt]()

        // val fromSeedToSoil = scala.collection.mutable.Map[BigInt,BigInt]()
        def fromSeedToSoil(s: BigInt): BigInt = {
            var t = BigInt(-1)
            for entry <- seedsToSoil do
                if (s >= entry.src && s < entry.src + entry.runLength) then
                    t = entry.dest + (s - entry.src)

            if t == -1 then s else t
        }

        def fromSoil2Fert(s: BigInt): BigInt = {
            var t = BigInt(-1)
            for entry <- soil2fert do
                if (s >= entry.src && s < entry.src + entry.runLength) then
                    t = entry.dest + (s - entry.src)

            if t == -1 then s else t
        }

        def fromFert2Water(s: BigInt): BigInt = {
            var t = BigInt(-1)
            for entry <- fert2water do
                if (s >= entry.src && s < entry.src + entry.runLength) then
                    t = entry.dest + (s - entry.src)

            if t == -1 then s else t
        }

        def fromWater2Light(s: BigInt): BigInt = {
            var t = BigInt(-1)
            for entry <- water2light do
                if (s >= entry.src && s < entry.src + entry.runLength) then
                    t = entry.dest + (s - entry.src)

            if t == -1 then s else t
        }

        def fromLight2Temp(s: BigInt): BigInt = {
            var t = BigInt(-1)
            for entry <- light2temp do
                if (s >= entry.src && s < entry.src + entry.runLength) then
                    t = entry.dest + (s - entry.src)

            if t == -1 then s else t
        }

        def fromTemp2Humidity(s: BigInt): BigInt = {
            var t = BigInt(-1)
            for entry <- temp2humidity do
                if (s >= entry.src && s < entry.src + entry.runLength) then
                    t = entry.dest + (s - entry.src)

            if t == -1 then s else t
        }

        def fromHumidity2Location(s: BigInt): BigInt = {
            var t = BigInt(-1)
            for entry <- humidity2location do
                if (s >= entry.src && s < entry.src + entry.runLength) then
                    t = entry.dest + (s - entry.src)

            if t == -1 then s else t
        }

        // strt with seed, find location
        for s <- seeds do
            var temp = BigInt(0)
            println(s"0. $s")
            temp = fromSeedToSoil(s)
            println(s"1. $temp")
            temp = fromSoil2Fert(temp)
            println(s"2. $temp")
            temp = fromFert2Water(temp)
            println(s"3. $temp")
            temp = fromWater2Light(temp)
            println(s"5. $temp")
            temp = fromLight2Temp(temp)
            println(s"6. $temp")
            temp = fromTemp2Humidity(temp)
            println(s"7. $temp")
            temp = fromHumidity2Location(temp)
            println(s"8. $temp")
            locs += temp
        end for

        val answerP1 = locs.min

        println(s"Part 1: What is the lowest location number that corresponds to any of the initial seed numbers?  A: $answerP1")
        val delta1 = Duration.between(p1T0, Instant.now())
        println(s"Part 1 run time approx ${delta1.toMillis} milliseconds\n")


        // ----------
        //  Part Two
        // ----------
        val p2T0 = Instant.now()

        println(s"Part 2: TBD ???")

        val delta2 = Duration.between(p2T0, Instant.now())
        println(f"Part 2 run time approx ${delta2.toMillis} milliseconds")

        // errata...for visualization with Excel chart

    }

end Day05
