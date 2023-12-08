import scala.io.Source

val input = Source.fromResource("07-test.txt").getLines().toVector

enum HandType:
    case HighCard, OnePair, TwoPair, Three, FullHouse, Four, Five

case class Hand(cards: String, bid: Int)

val parseHandRE = raw"([\d\w]+) ([\d ]+)".r
val a = "32T3K 765"
a.split(" +")

val z = input.map(y=>y.trim.split(" +")).map(x => Hand(x(0), x(1).toInt)).toList

z.foreach(println)

val listHands = input
    .map(x => parseHandRE.findAllIn(x).matchData.toVector)

def buildListHands(input: Vector[String]): List[Hand] = {
    input
        .map(x => parseHandRE.findAllIn(x).toVector)
        .map(x => Hand(x(0).trim, x(1).trim.toInt))
        .toList
}

//val listHands = buildListHands(input)
//
//listHands.foreach(println)