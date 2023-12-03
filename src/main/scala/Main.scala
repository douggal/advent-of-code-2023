import java.time.ZonedDateTime

@main
def Main(day: Int = 3, runType: Int = 1): Unit = {

    val title = "Advent of Code 2023"

    // for each day's puzzle, create an instance of class and supply an
    // input file type, called runType:
    // runType = 1 for test data
    //         = 2 for real data

     // val runType = 1 // Default to type =1, the test data

    // Which day's puzzle to run?
    // val day = 3

    // Implement each day's puzzle
    val d1 = Day01(s"$title Day 1", runType)
    val d2 = Day02(s"$title Day 2", runType)
    val d3 = Day03(s"$title Day 3", runType)

    // Run the selected day's puzzle
    println(s"Begin: ${ZonedDateTime.now()}")

    day match {
        case 3 => d3.run()
        case 2 => d2.run()
        case 1 => d1.run()
        case _ => println("Error day does not match with a class")
    }

    println(s"\nEnd: ${ZonedDateTime.now()}")
}