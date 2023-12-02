import java.time.ZonedDateTime

@main
def Main(): Unit = {

    // for each day's puzzle, create an instance of class and supply an
    // input file type, called runType:
    // runType = 1 for test data
    //         = 2 for real data

    val title = "Advent of Code 2023"
    val runType = 1 // Default to type =1, the test data

    // Which day's puzzle to run?
    val day = 2

    // Implement each day's puzzle
    val d1 = Day01(s"$title Day 1", runType)
    val d2 = Day02(s"$title Day 2", runType)

    // Run the selected day's puzzle
    println(s"Begin: ${ZonedDateTime.now()}")

    day match {
        case 2 => d2.run()
        case 1 => d1.run()
        case _ => println("Error day does not match with a class")
    }

    println(s"\nEnd: ${ZonedDateTime.now()}")
}